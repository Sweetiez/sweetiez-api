//package fr.sweetiez.api.adapter.delivery.tray;
//
//import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
//import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;
//import fr.sweetiez.api.core.trays.models.requests.*;
//import fr.sweetiez.api.core.trays.models.responses.AdminDetailedTrayResponse;
//import fr.sweetiez.api.core.trays.models.responses.AdminTraySimpleResponse;
//import fr.sweetiez.api.core.trays.models.responses.SimpleTrayResponse;
//import fr.sweetiez.api.core.trays.models.tray.Tray;
//import fr.sweetiez.api.core.trays.services.TrayService;
//import io.minio.GetPresignedObjectUrlArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.RemoveObjectArgs;
//import io.minio.http.Method;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URI;
//import java.util.Collection;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class AdminTrayEndPoints {
//
//    private final MinioClient minioClient;
//
//    @Value("${minio.bucket.name}")
//    private String bucketName;
//    private final TrayService trayService;
//
//    public AdminTrayEndPoints(TrayService trayService, MinioClient minioClient) {
//        this.trayService = trayService;
//        this.minioClient = minioClient;
//    }
//
//    public ResponseEntity<Object> create(CreateTrayRequest request) {
//        try {
//            var createdSweet = trayService.createSweet(request);
//            return ResponseEntity
//                    .created(URI.create("/trays/" + createdSweet.id().value()))
//                    .build();
//        }
//        catch (InvalidFieldsException exception) {
//            return ResponseEntity.badRequest().build();
//        }
//        catch (SweetAlreadyExistsException exception) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }
//
//    public ResponseEntity<Tray> publish(PublishTrayRequest request) {
//        try {
//            var sweet = trayService.publishSweet(request);
//            return ResponseEntity.ok(sweet);
//        }
//        catch (NoSuchElementException exception) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    public ResponseEntity<Tray> unpublish(UnpublishTrayRequest request) {
//        try {
//            var sweet = trayService.unPublishSweet(request);
//            return ResponseEntity.ok(sweet);
//        }
//        catch (NoSuchElementException exception) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    public ResponseEntity<Collection<AdminTraySimpleResponse>> retrieveAllTrays() {
//        var allSweets = trayService.retrieveAllTrays().content()
//                .stream()
//                .map(AdminTraySimpleResponse::new)
//                .collect(Collectors.toSet());
//
//        return ResponseEntity.ok(allSweets);
//    }
//
//    public ResponseEntity<AdminDetailedTrayResponse> adminRetrieveTrayDetails(String id) {
//        try {
//            return ResponseEntity.ok(trayService.adminRetrieveTrayDetails(id));
//        }
//        catch (NoSuchElementException exception) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    public ResponseEntity<SimpleTrayResponse> addImage(String id, MultipartFile image) {
//        var imageName = String.format("tray_%s_%s", image.getOriginalFilename(), UUID.randomUUID());
//        // Store the image in the minio bucket
//        try {
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(imageName)
//                    .stream(image.getInputStream(), image.getSize(), -1)
//                    .build());
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .header("Error", "Error while uploading file")
//                    .build();
//        }
//        // Return the image URL
//        String url;
//        try {
//            url = minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.DELETE)
//                            .bucket(bucketName)
//                            .object(imageName)
//                            .build());
//        }
//        catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .header("Error", "Error retrieving file")
//                    .build();
//        }
//
//        url = url.substring(0, url.indexOf('?'));
//        return ResponseEntity.ok(trayService.addImageToTray(id, url));
//    }
//
//    public ResponseEntity<AdminDetailedTrayResponse> adminUpdateSweetDetails(UpdateTrayRequest request) {
//        try {
//            return ResponseEntity.ok(trayService.adminUpdateTrayDetails(request));
//        }
//        catch (NoSuchElementException exception) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    public ResponseEntity<SimpleTrayResponse> deleteImage(String id, DeleteImageRequest request) {
//        try {
//            var objectName = request.imageUrl().substring(request.imageUrl().lastIndexOf('/') + 1);
//            System.out.println(objectName);
//            minioClient.removeObject(
//                    RemoveObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .build());
//
//            return ResponseEntity.ok(trayService.adminDeleteImageFromTray(id, request));
//        }
//        catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .header("Error", "Error while deleting file")
//                    .build();
//        }
//    }
//}
