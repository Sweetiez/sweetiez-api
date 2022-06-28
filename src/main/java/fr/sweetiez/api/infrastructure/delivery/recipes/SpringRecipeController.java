package fr.sweetiez.api.infrastructure.delivery.recipes;

import fr.sweetiez.api.adapter.delivery.RecipeEndPoints;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.recipes.models.responses.SimpleRecipeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/recipes")
public class SpringRecipeController {

    private final RecipeEndPoints recipesEndPoints;


    public SpringRecipeController(RecipeEndPoints recipesEndPoints) {
        this.recipesEndPoints = recipesEndPoints;
    }

    @GetMapping
    public ResponseEntity<Collection<SimpleRecipeResponse>> retrievePublishedRecipes() {
        return recipesEndPoints.retrievePublishedRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailedResponse> retrieveRecipeDetails(@PathVariable("id") String id) {
        return recipesEndPoints.retrieveRecipeDetails(id);
    }
}
