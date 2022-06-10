package fr.sweetiez.api.infrastructure.delivery.recipes;

import fr.sweetiez.api.adapter.delivery.AdminRecipeEndPoints;
import fr.sweetiez.api.core.recipes.models.requests.ChangeStepsOrderRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/step")
    public ResponseEntity<RecipeDetailedResponse> changeStepsOrder(@RequestBody ChangeStepsOrderRequest request) {
        return recipesEndPoints.changeStepOrder(request);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDetailedResponse>> retrieveAll() {
        return recipesEndPoints.retrieveAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailedResponse> retrieveById(@PathVariable String id) {
        return recipesEndPoints.retrieveById(id);
    }


}
