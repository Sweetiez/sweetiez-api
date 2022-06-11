package fr.sweetiez.api.infrastructure.delivery.recipes;

import fr.sweetiez.api.adapter.delivery.AdminRecipeEndPoints;
import fr.sweetiez.api.core.recipes.models.requests.*;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.sweets.models.requests.DeleteImageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @DeleteMapping("/step")
    public ResponseEntity<RecipeDetailedResponse> deleteStepsOrder(@RequestBody RemoveStepRequest request) {
        return recipesEndPoints.deleteStep(request);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDetailedResponse>> retrieveAll() {
        return recipesEndPoints.retrieveAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailedResponse> retrieveById(@PathVariable String id) {
        return recipesEndPoints.retrieveById(id);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<RecipeDetailedResponse> addImage(@PathVariable("id") String id, @RequestParam MultipartFile image) {
        return recipesEndPoints.addImage(id, image);
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<RecipeDetailedResponse> deleteImage(@PathVariable("id") String id, @RequestBody DeleteImageRequest request) {
        return recipesEndPoints.deleteImage(id, request);
    }

    @PutMapping("/publish")
    public ResponseEntity<RecipeDetailedResponse> publishRecipe(@RequestBody PublishRecipeRequest request) {
        return recipesEndPoints.publish(request);
    }

    @DeleteMapping("/publish")
    public ResponseEntity<RecipeDetailedResponse> unPublishRecipe(@RequestBody UnPublishRecipeRequest request) {
        return recipesEndPoints.unPublish(request);
    }

}
