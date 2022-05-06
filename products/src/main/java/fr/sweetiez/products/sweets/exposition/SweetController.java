package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.domain.Sweet;
import fr.sweetiez.products.sweets.domain.Sweets;
import fr.sweetiez.products.sweets.domain.exceptions.InvalidFieldsException;
import fr.sweetiez.products.sweets.domain.exceptions.SweetAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sweets")
@RequiredArgsConstructor
public class SweetController {

    private final Sweets service;

    @PostMapping
    public ResponseEntity<Sweet> create(@RequestBody CreateSweetRequest request) {
        try {
            Sweet sweet = service.create(new Sweet(request), request.getCreator());

            return ResponseEntity
                    .created(URI.create("/sweets/" + sweet.getId().toString()))
                    .build();
        }
        catch (InvalidFieldsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (SweetAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/published")
    public ResponseEntity<Set<Sweet>> findAllPublished() {
        var publishedSweets = service.all().stream()
                .filter(sweet -> sweet.getState().equals(State.PUBLISHED))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(publishedSweets);
    }

    @PutMapping("/publish")
    public ResponseEntity<Sweet> publish(@RequestBody PublishSweetRequest request) {
        try {
            Sweet sweet = service.publish(request.getId().toString(), request.getHighlight(), request.getEmployee());
            return ResponseEntity.ok(sweet);
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
