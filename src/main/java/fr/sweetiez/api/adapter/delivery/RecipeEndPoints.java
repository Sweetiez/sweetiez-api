package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.recipes.models.responses.SimpleRecipeResponse;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import fr.sweetiez.api.core.recipes.services.exceptions.InvalidRecipeException;
import fr.sweetiez.api.core.recipes.services.exceptions.RecipeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class RecipeEndPoints {
    private final RecipeService recipeService;


    public RecipeEndPoints(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public ResponseEntity<Collection<SimpleRecipeResponse>> retrievePublishedRecipes() {
        return ResponseEntity.ok(recipeService.retrievePublishedRecipes().recipes().stream()
                .map(SimpleRecipeResponse::new)
                .toList());
    }

    public ResponseEntity<RecipeDetailedResponse> retrieveRecipeDetails(String id) {
        try {
            var recipe = recipeService.retrieveById(id);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
