package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
import org.springframework.http.ResponseEntity;

public class IngredientEndPoints {

    private final IngredientService ingredientService;

    public IngredientEndPoints(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public ResponseEntity<Ingredient> create(String ingredientName) {
        try {
            var ingredient = ingredientService.create(ingredientName);
            return ResponseEntity.ok(ingredient);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
