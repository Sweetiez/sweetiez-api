package fr.sweetiez.api.adapter.delivery.ingredient;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.models.IngredientResponse;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
import fr.sweetiez.api.core.products.services.SweetService;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

public class IngredientEndPoints {

    private final IngredientService ingredientService;

    private final SweetService sweetService;

    public IngredientEndPoints(IngredientService ingredientService, SweetService sweetService) {
        this.ingredientService = ingredientService;
        this.sweetService = sweetService;
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

    public ResponseEntity<Collection<IngredientResponse>> retrieveAll() {
        try {
            var ingredients = ingredientService.retrieveAll();
            return ResponseEntity.ok(ingredients);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> deleteIngredient(UUID id) {
        try {
            sweetService.deleteIngredientContainedInSweets(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
