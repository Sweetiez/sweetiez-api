package fr.sweetiez.api.infrastructure.delivery.sweet;

import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
import fr.sweetiez.api.core.products.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.ProductBannerResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sweets")
public class SpringSweetController {

    private final SweetEndPoints sweetsEndPoints;

    public SpringSweetController(SweetEndPoints sweetsEndPoints) {
        this.sweetsEndPoints = sweetsEndPoints;
    }

    @GetMapping("/published")
    public ResponseEntity<Collection<SimpleProductResponse>> retrievePublishedSweets() {
        return sweetsEndPoints.retrievePublishedSweets();
    }

    @GetMapping("/banner")
    public ResponseEntity<Collection<ProductBannerResponse>> retrieveBannerSweets() {
        return sweetsEndPoints.retrieveBannerSweets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(@PathVariable("id") String id) {
        return sweetsEndPoints.retrieveSweetDetails(id);
    }

}
