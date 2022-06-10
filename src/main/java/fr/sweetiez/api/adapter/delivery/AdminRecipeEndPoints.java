package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import fr.sweetiez.api.core.recipes.services.exceptions.InvalidRecipeException;
import fr.sweetiez.api.core.recipes.services.exceptions.RecipeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AdminRecipeEndPoints {

    private final RecipeService recipeService;


    public AdminRecipeEndPoints(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public ResponseEntity<RecipeDetailedResponse> create(CreateRecipeRequest request) {
        System.out.println(request);
        var recipe = recipeService.createRecipe(request);

        return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
    }

    public ResponseEntity<RecipeDetailedResponse> addStep(CreateStepRequest request) {
//        System.out.println(request);
        var recipe = recipeService.addStep(request);

        return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
    }

    public ResponseEntity<List<RecipeDetailedResponse>> retrieveAll() {
        var recipes = recipeService.retrieveAll();

        return ResponseEntity.ok(recipes.recipes().stream()
                .map(RecipeDetailedResponse::new)
                .toList());
    }

    public ResponseEntity<RecipeDetailedResponse> retrieveById(String id) {
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
