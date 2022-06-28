package fr.sweetiez.api.infrastructure.delivery.tray;

import fr.sweetiez.api.adapter.delivery.tray.TrayEndPoints;
import fr.sweetiez.api.core.products.models.responses.DetailedTrayResponse;
import fr.sweetiez.api.core.products.models.responses.ProductBannerResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/trays")
public class SpringTrayController {

    private final TrayEndPoints trayEndPoints;

    public SpringTrayController(TrayEndPoints trayEndPoints) {
        this.trayEndPoints = trayEndPoints;
    }

    @GetMapping("/published")
    public ResponseEntity<Collection<SimpleProductResponse>> retrievePublishedSweets() {
        return trayEndPoints.retrievePublishedTrays();
    }

    @GetMapping("/banner")
    public ResponseEntity<Collection<ProductBannerResponse>> retrieveBannerSweets() {
        return trayEndPoints.retrieveBannerTrays();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedTrayResponse> retrieveSweetDetails(@PathVariable("id") UUID id) {
        return trayEndPoints.retrieveTrayDetails(id);
    }

}
