package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.IngredientEndPoints;
import fr.sweetiez.api.core.ingredients.models.CreateIngredientRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
