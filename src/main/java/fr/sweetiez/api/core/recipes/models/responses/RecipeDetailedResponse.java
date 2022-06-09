package fr.sweetiez.api.core.recipes.models.responses;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDifficulty;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;

import java.util.Collection;

public record RecipeDetailedResponse(String id,
                                     String title,
                                     String description,
                                     RecipeDifficulty difficulty,
                                     Integer cost,
                                     Integer people,
                                     Integer preparationTime,
                                     Integer chillTime,
                                     Integer cookTime,
                                     Collection<String> images,
                                     Collection<Step> steps) {

    public RecipeDetailedResponse(Recipe recipe) {
        this(
          recipe.id().id().toString(),
          recipe.title().value(),
          recipe.detail().description(),
          recipe.detail().difficulty(),
          recipe.detail().cost(),
          recipe.detail().people(),
          recipe.detail().cookTime().preparationTime(),
          recipe.detail().cookTime().chillTime(),
          recipe.detail().cookTime().cookTime(),
          recipe.images(),
          recipe.steps().steps()
        );
    }

}

