package fr.sweetiez.api.infrastructure.delivery.sweet;

import fr.sweetiez.api.adapter.delivery.sweet.AdminSweetEndPoints;
import fr.sweetiez.api.core.products.models.requests.*;
import fr.sweetiez.api.core.products.models.responses.AdminDetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.AdminSimpleProductResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/admin/sweets")
public class SpringAdminSweetController {

    private final AdminSweetEndPoints sweetsEndPoints;

    public SpringAdminSweetController(AdminSweetEndPoints sweetsEndPoints) {
        this.sweetsEndPoints = sweetsEndPoints;
    }

    @PostMapping
    public ResponseEntity<Object> createSweet(@RequestBody CreateSweetRequest request) {
        return sweetsEndPoints.create(request);
    }

    @GetMapping()
    public ResponseEntity<Collection<AdminSimpleProductResponse>> adminRetrieveAllSweets() {
        return sweetsEndPoints.retrieveAllSweets();
    }

    @PutMapping("/publish")
    public ResponseEntity<SimpleProductResponse> publishSweet(@RequestBody PublishProductRequest request) {
        return sweetsEndPoints.publish(request);
    }

    @DeleteMapping("/publish")
    public ResponseEntity<SimpleProductResponse> unPublishSweet(@RequestBody UnpublishProductRequest request) {
        return sweetsEndPoints.unpublish(request);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<SimpleProductResponse> addImage(
            @PathVariable("id") String id, @RequestParam MultipartFile image)
    {
        return sweetsEndPoints.addImage(id, image);
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<SimpleProductResponse> deleteImage(
            @PathVariable("id") String id, @RequestBody DeleteImageRequest request)
    {
        return sweetsEndPoints.deleteImage(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailedSweetResponse> adminRetrieveSweetDetails(@PathVariable("id") UUID id) {
        return sweetsEndPoints.adminRetrieveSweetDetails(id);
    }

    @PutMapping()
    public ResponseEntity<AdminDetailedSweetResponse> adminUpdateSweet(@RequestBody UpdateSweetRequest request) {
        return sweetsEndPoints.adminUpdateSweetDetails(request);
    }

}
