package fr.sweetiez.exposition;

import fr.sweetiez.sweets.domain.Status;
import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;
import fr.sweetiez.sweets.domain.exceptions.InvalidIngredientsException;
import fr.sweetiez.sweets.domain.exceptions.InvalidPriceException;
import fr.sweetiez.sweets.domain.exceptions.InvalidSweetNameException;
import fr.sweetiez.sweets.domain.exceptions.SweetAlreadyExistsException;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;
import fr.sweetiez.sweets.use_cases.PublishSweetRequest;
import fr.sweetiez.sweets.use_cases.AnySweetFoundException;
import fr.sweetiez.sweets.use_cases.CreateSweet;
import fr.sweetiez.sweets.use_cases.PublishSweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sweets")
public class SweetController {

    private final Sweets sweetsRepository;

    @Autowired
    public SweetController(Sweets sweetsRepository) {
        this.sweetsRepository = sweetsRepository;
    }


    @PostMapping
    public ResponseEntity<Sweet> create(@RequestBody CreateSweetRequest request) {
        try {
            CreateSweet useCase = new CreateSweet(sweetsRepository);
            Sweet sweet = useCase.create(request);
            return ResponseEntity.created(URI.create("/sweets/" + sweet.getId().toString())).build();
        }
        catch (InvalidSweetNameException
                | InvalidPriceException
                | InvalidIngredientsException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (SweetAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/published")
    public ResponseEntity<Set<Sweet>> findAllPublished() {
        var publishedSweets = sweetsRepository.all().stream()
                .filter(sweet -> sweet.getStatus().equals(Status.PUBLISHED))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(publishedSweets);
    }

    @PutMapping("/publish")
    public ResponseEntity<Sweet> publish(@RequestBody PublishSweetRequest request) {
        PublishSweet useCase = new PublishSweet(sweetsRepository);

        try {
            Sweet sweet = useCase.publish(request.getId(), request.getPriority());
            return ResponseEntity.ok(sweet);
        }
        catch (AnySweetFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
