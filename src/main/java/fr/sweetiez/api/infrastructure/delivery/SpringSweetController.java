package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sweets")
public class SpringSweetController {

    private final SweetEndPoints sweetsEndPoints;

    public SpringSweetController(SweetEndPoints sweetsEndPoints) {
        this.sweetsEndPoints = sweetsEndPoints;
    }

    @PostMapping
    public ResponseEntity<Object> createSweet(@RequestBody CreateSweetRequest request) {
        return sweetsEndPoints.create(request);
    }

    @GetMapping("/published")
    public ResponseEntity<Sweets> retrievePublishedSweets() {
        return sweetsEndPoints.retrievePublishedSweets();
    }

    @PutMapping("/publish")
    public ResponseEntity<Sweet> publishSweet(@RequestBody PublishSweetRequest request) {
        return sweetsEndPoints.publish(request);
    }
}
