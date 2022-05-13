package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SweetEndPoints {

    private final SweetService sweetService;

    public SweetEndPoints(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    public ResponseEntity<Object> create(CreateSweetRequest request) {
        try {
            var createdSweet = sweetService.createSweet(request);
            return ResponseEntity
                    .created(URI.create("/sweets/" + createdSweet.id().value()))
                    .build();
        }
        catch (InvalidFieldsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (SweetAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Sweet> publish(PublishSweetRequest request) {
        try {
            var sweet = sweetService.publishSweet(request);
            return ResponseEntity.ok(sweet);
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Collection<SimpleSweetResponse>> retrievePublishedSweets() {
        var publishedSweets = sweetService.retrievePublishedSweets().content()
                .stream()
                .map(SimpleSweetResponse::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(publishedSweets);
    }
}
