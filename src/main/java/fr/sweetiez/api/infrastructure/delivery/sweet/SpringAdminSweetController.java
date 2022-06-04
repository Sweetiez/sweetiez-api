package fr.sweetiez.api.infrastructure.delivery.sweet;

import fr.sweetiez.api.adapter.delivery.AdminSweetEndPoints;
import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.core.sweets.models.requests.*;
import fr.sweetiez.api.core.sweets.models.responses.AdminDetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.AdminSweetSimpleResponse;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

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
    public ResponseEntity<Collection<AdminSweetSimpleResponse>> adminRetrieveAllSweets() {
        return sweetsEndPoints.retrieveAllSweets();
    }

    @PutMapping("/publish")
    public ResponseEntity<Sweet> publishSweet(@RequestBody PublishSweetRequest request) {
        return sweetsEndPoints.publish(request);
    }

    @DeleteMapping("/publish")
    public ResponseEntity<Sweet> unPublishSweet(@RequestBody UnPublishSweetRequest request) {
        return sweetsEndPoints.unPublish(request);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<SimpleSweetResponse> addImage(@PathVariable("id") String id, @RequestParam MultipartFile image) {
        return sweetsEndPoints.addImage(id, image);
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<SimpleSweetResponse> deleteImage(@PathVariable("id") String id, @RequestBody DeleteImageRequest request) {
        return sweetsEndPoints.deleteImage(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailedSweetResponse> adminRetrieveSweetDetails(@PathVariable("id") String id) {
        return sweetsEndPoints.adminRetrieveSweetDetails(id);
    }

    @PutMapping()
    public ResponseEntity<AdminDetailedSweetResponse> adminUpdateSweet(@RequestBody UpdateSweetRequest request) {
        return sweetsEndPoints.adminUpdateSweetDetails(request);
    }

}
