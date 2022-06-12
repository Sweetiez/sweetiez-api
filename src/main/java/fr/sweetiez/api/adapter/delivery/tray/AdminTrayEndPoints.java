package fr.sweetiez.api.adapter.delivery.tray;


import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.requests.*;
import fr.sweetiez.api.core.products.models.responses.AdminDetailedTrayResponse;
import fr.sweetiez.api.core.products.models.responses.AdminSimpleProductResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.core.products.services.TrayService;
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

public class AdminTrayEndPoints {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final TrayService trayService;

    public AdminTrayEndPoints(TrayService trayService, MinioClient minioClient) {
        this.trayService = trayService;
        this.minioClient = minioClient;
    }

    public ResponseEntity<Object> create(CreateProductRequest request) {
        try {
            var createdSweet = trayService.create(request);
            return ResponseEntity
                    .created(URI.create("/trays/" + createdSweet.id().value()))
                    .build();
        }
        catch (InvalidFieldsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (ProductAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Tray> publish(PublishProductRequest request) {
        try {
            var sweet = trayService.publish(request);
            return ResponseEntity.ok(sweet);
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Tray> unpublish(UnpublishProductRequest request) {
        try {
            var sweet = trayService.unpublish(request);
            return ResponseEntity.ok(sweet);
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Collection<AdminSimpleProductResponse>> retrieveAllTrays() {
        var allSweets = trayService.retrieveAll()
                .stream()
                .map(AdminSimpleProductResponse::new)
                .toList();

        return ResponseEntity.ok(allSweets);
    }

    public ResponseEntity<AdminDetailedTrayResponse> adminRetrieveTrayDetails(UUID id) {
        try {
            return ResponseEntity.ok(trayService.adminRetrieveDetailsOf(new ProductID(id)));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<SimpleProductResponse> addImage(UUID id, MultipartFile image) {
        var imageName = String.format("tray_%s_%s", image.getOriginalFilename(), UUID.randomUUID());
        // Store the image in the minio bucket
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(imageName)
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .header("Error", "Error while uploading file")
                    .build();
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
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .header("Error", "Error retrieving file")
                    .build();
        }

        url = url.substring(0, url.indexOf('?'));
        return ResponseEntity.ok(trayService.addImageTo(new ProductID(id), url));
    }

    public ResponseEntity<AdminDetailedTrayResponse> adminUpdateSweetDetails(UpdateProductRequest request) {
        try {
            return ResponseEntity.ok(trayService.adminUpdateTrayDetails(request));
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

            return ResponseEntity.ok(trayService.adminDeleteImageFrom(new ProductID(id), request));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .header("Error", "Error while deleting file")
                    .build();
        }
    }
}
