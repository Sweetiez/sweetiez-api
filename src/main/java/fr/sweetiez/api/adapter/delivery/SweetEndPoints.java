package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SweetEndPoints {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final SweetService sweetService;

    public SweetEndPoints(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    public ResponseEntity<Object> create(CreateSweetRequest request) {
        try {
            var createdSweet = sweetService.createSweet(request);
            return ResponseEntity
                    .created(URI.create("/sweets/" + createdSweet.id().value()))
                    .build();
        }
        catch (InvalidFieldsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (SweetAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Sweet> publish(PublishSweetRequest request) {
        try {
            var sweet = sweetService.publishSweet(request);
            return ResponseEntity.ok(sweet);
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Collection<SimpleSweetResponse>> retrievePublishedSweets() {
        var publishedSweets = sweetService.retrievePublishedSweets().content()
                .stream()
                .map(SimpleSweetResponse::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(publishedSweets);
    }

    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(String id) {
        try {
            return ResponseEntity.ok(sweetService.retrieveSweetDetails(id));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<DetailedSweetResponse> addImage(String id, MultipartFile image) {
        // Store the image in the minio bucket
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(image.getOriginalFilename())
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .build());
        } catch (Exception e) {
            System.out.println("Error while uploading file");
            throw new RuntimeException(e);
        }
        // Return the image URL
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.DELETE)
                            .bucket(bucketName)
                            .object(image.getOriginalFilename())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        url = url.substring(0, url.indexOf('?'));

        return ResponseEntity.ok(sweetService.addImageToSweet(id, url));
    }
}
