package fr.sweetiez.api.infrastructure.delivery.recipes;

import fr.sweetiez.api.adapter.delivery.AdminRecipeEndPoints;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/recipes")
public class SpringAdminRecipeController {

    private final AdminRecipeEndPoints recipesEndPoints;

    public SpringAdminRecipeController(AdminRecipeEndPoints recipesEndPoints) {
        this.recipesEndPoints = recipesEndPoints;
    }

    @PostMapping
    public ResponseEntity<RecipeDetailedResponse> createRecipe(@RequestBody CreateRecipeRequest request) {
        return recipesEndPoints.create(request);
    }

    @PostMapping("/step")
    public ResponseEntity<RecipeDetailedResponse> addStep(@RequestBody CreateStepRequest request) {
        return recipesEndPoints.addStep(request);
    }


}
