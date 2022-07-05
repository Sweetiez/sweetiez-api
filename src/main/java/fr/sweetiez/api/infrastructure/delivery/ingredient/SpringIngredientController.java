package fr.sweetiez.api.infrastructure.delivery.ingredient;

import fr.sweetiez.api.adapter.delivery.ingredient.IngredientEndPoints;
import fr.sweetiez.api.core.ingredients.models.CreateIngredientRequest;
import fr.sweetiez.api.core.ingredients.models.IngredientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

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

    @DeleteMapping("/admin/ingredients/{id}")
    @Transactional
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id)
    {
        return endPoints.deleteIngredient(id);
    }
}
