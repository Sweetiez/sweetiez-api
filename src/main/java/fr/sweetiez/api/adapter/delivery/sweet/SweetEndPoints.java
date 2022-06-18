package fr.sweetiez.api.adapter.delivery.sweet;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.ProductBannerResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.core.products.services.SweetService;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

public class SweetEndPoints {

    private final SweetService sweetService;

    public SweetEndPoints(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    public ResponseEntity<Collection<SimpleProductResponse>> retrievePublishedSweets() {
        var publishedSweets = sweetService.retrieveAllPublished()
                .stream()
                .map(sweet -> new SimpleProductResponse(sweet, ProductType.SWEET))
                .toList();

        return ResponseEntity.ok(publishedSweets);
    }

    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(UUID id) {
        try {
            return ResponseEntity.ok(sweetService.retrieveDetailsOf(new ProductID(id)));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<ProductBannerResponse>> retrieveBannerSweets() {
        var bannerSweets = sweetService.retrieveAllPublished()
                .stream()
                .filter(sweet -> sweet.details().characteristics().highlight() == Highlight.BANNER)
                .map(ProductBannerResponse::new)
                .toList();

        return ResponseEntity.ok(bannerSweets);
    }
}
