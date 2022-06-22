package fr.sweetiez.api.infrastructure.delivery.ingredient;

import fr.sweetiez.api.adapter.delivery.ingredient.IngredientEndPoints;
import fr.sweetiez.api.core.ingredients.models.CreateIngredientRequest;
import fr.sweetiez.api.core.ingredients.models.IngredientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SpringIngredientController {

    private final IngredientEndPoints endPoints;

    public SpringIngredientController(IngredientEndPoints endPoints) {
        this.endPoints = endPoints;
    }

    @PostMapping("/admin/ingredients")
    public ResponseEntity<?> create(@RequestBody CreateIngredientRequest request) {
        return endPoints.create(request.name());
    }

    @GetMapping("/admin/ingredients")
    public ResponseEntity<Collection<IngredientResponse>> retrieveAll() {
        return endPoints.retrieveAll();
    }
}
