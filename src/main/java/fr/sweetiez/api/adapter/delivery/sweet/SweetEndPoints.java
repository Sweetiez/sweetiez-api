package fr.sweetiez.api.adapter.delivery.sweet;

import fr.sweetiez.api.core.sweets.models.responses.BannerSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.services.SweetService;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.NoSuchElementException;

public class SweetEndPoints {

    private final SweetService sweetService;

    public SweetEndPoints(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    public ResponseEntity<Collection<SimpleSweetResponse>> retrievePublishedSweets() {
        var publishedSweets = sweetService.retrievePublishedSweets().content()
                .stream()
                .map(SimpleSweetResponse::new)
                .toList();

        return ResponseEntity.ok(publishedSweets);
    }

    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(String id) {
        try {
            return ResponseEntity.ok(sweetService.retrieveSweetDetails(id));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<BannerSweetResponse>> retrieveBannerSweets() {
        var bannerSweets = sweetService.retrievePublishedSweets().content()
                .stream()
                .filter(sweet -> sweet.states().highlight() == Highlight.BANNER)
                .map(BannerSweetResponse::new)
                .toList();

        return ResponseEntity.ok(bannerSweets);
    }
}
