package fr.sweetiez.sweets.exposition;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;
import fr.sweetiez.sweets.use_cases.CreateSweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/sweets")
public class SweetController {

    private final Sweets sweetsRepository;

    public SweetController(Sweets sweetsRepository) {
        this.sweetsRepository = sweetsRepository;
    }


    @PostMapping
    public ResponseEntity<Sweet> create(@RequestBody SweetDTO sweetDTO) {
        try {
            CreateSweet useCase = new CreateSweet(sweetsRepository);
            Sweet sweet = useCase.create(sweetDTO);
            return ResponseEntity.created(URI.create("/sweets/" + sweet.getId().toString())).build();

        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
