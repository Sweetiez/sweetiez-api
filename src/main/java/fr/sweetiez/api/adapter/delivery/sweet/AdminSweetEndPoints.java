package fr.sweetiez.api.adapter.delivery.sweet;

import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.requests.*;
import fr.sweetiez.api.core.products.models.responses.AdminDetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.AdminSimpleProductResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.products.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.products.services.exceptions.ProductAlreadyExistsException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminSweetEndPoints {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final SweetService sweetService;

    public AdminSweetEndPoints(SweetService sweetService, MinioClient minioClient) {
        this.sweetService = sweetService;
        this.minioClient = minioClient;
    }

    public ResponseEntity<Object> create(CreateSweetRequest request) {
        try {
            var createdSweet = sweetService.create(request);
            return ResponseEntity
                    .created(URI.create("/sweets/" + createdSweet.id().value()))
                    .build();
        }
        catch (InvalidFieldsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (ProductAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<SimpleProductResponse> publish(PublishProductRequest request) {
        try {
            var sweet = sweetService.publish(request);
            return ResponseEntity.ok(new SimpleProductResponse(sweet));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<SimpleProductResponse> unpublish(UnpublishProductRequest request) {
        try {
            var sweet = sweetService.unpublish(request);
            return ResponseEntity.ok(new SimpleProductResponse(sweet));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Collection<AdminSimpleProductResponse>> retrieveAllSweets() {
        var allSweets = sweetService.retrieveAll()
                .stream()
                .map(AdminSimpleProductResponse::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(allSweets);
    }

    public ResponseEntity<AdminDetailedSweetResponse> adminRetrieveSweetDetails(UUID id) {
        try {
            return ResponseEntity.ok(sweetService.adminRetrieveDetailsOf(new ProductID(id)));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<SimpleProductResponse> addImage(UUID id, MultipartFile image) {
        var imageName = String.format("sweet_%s_%s", image.getOriginalFilename(), UUID.randomUUID());
        // Store the image in the minio bucket
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(imageName)
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("Error", "Error while uploading file").build();
        }
        // Return the image URL
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.DELETE)
                            .bucket(bucketName)
                            .object(imageName)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("Error", "Error retrieving file").build();
        }
        url = url.substring(0, url.indexOf('?'));

        return ResponseEntity.ok(sweetService.addImageTo(new ProductID(id), url));
    }

    public ResponseEntity<AdminDetailedSweetResponse> adminUpdateSweetDetails(UpdateSweetRequest request) {
        try {
            return ResponseEntity.ok(sweetService.adminUpdateSweetDetails(request));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<SimpleProductResponse> deleteImage(UUID id, DeleteImageRequest request) {

        try {
            var objectName = request.imageUrl().substring(request.imageUrl().lastIndexOf('/') + 1);
            System.out.println(objectName);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

            return ResponseEntity.ok(sweetService.adminDeleteImageFrom(new ProductID(id), request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("Error", "Error while deleting file").build();
        }


    }
}
