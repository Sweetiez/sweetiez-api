package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import org.springframework.http.ResponseEntity;

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
}
