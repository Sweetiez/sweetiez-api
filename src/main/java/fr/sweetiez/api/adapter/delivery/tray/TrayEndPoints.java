package fr.sweetiez.api.adapter.delivery.tray;

import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.responses.DetailedTrayResponse;
import fr.sweetiez.api.core.products.models.responses.ProductBannerResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.core.products.services.TrayService;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

public class TrayEndPoints {

    private final TrayService trayService;

    public TrayEndPoints(TrayService trayService) {
        this.trayService = trayService;
    }

    public ResponseEntity<Collection<SimpleProductResponse>> retrievePublishedTrays() {
        var publishedSweets = trayService.retrieveAllPublished()
                .stream()
                .map(SimpleProductResponse::new)
                .toList();

        return ResponseEntity.ok(publishedSweets);
    }

    public ResponseEntity<DetailedTrayResponse> retrieveTrayDetails(UUID id) {
        try {
            return ResponseEntity.ok(trayService.retrieveDetailsOf(new ProductID(id)));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<ProductBannerResponse>> retrieveBannerTrays() {
        var bannerTrays = trayService.retrieveAllPublished()
                .stream()
                .filter(tray -> tray.details().characteristics().highlight() == Highlight.BANNER)
                .map(ProductBannerResponse::new)
                .toList();

        return ResponseEntity.ok(bannerTrays);
    }
}
