package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.AdminDetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.AdminSweetSimpleResponse;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/sweets")
public class SpringSweetController {

    private final SweetEndPoints sweetsEndPoints;

    public SpringSweetController(SweetEndPoints sweetsEndPoints) {
        this.sweetsEndPoints = sweetsEndPoints;
    }

    @GetMapping("/published")
    public ResponseEntity<Collection<SimpleSweetResponse>> retrievePublishedSweets() {
        return sweetsEndPoints.retrievePublishedSweets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedSweetResponse> retrieveSweetDetails(@PathVariable("id") String id) {
        return sweetsEndPoints.retrieveSweetDetails(id);
    }

}
