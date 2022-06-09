package fr.sweetiez.api.core.recipes.models.recipes.details;

import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;

public record RecipeDetail(String description,
                           RecipeDifficulty difficulty,
                           Integer cost,
                           Integer people,
                           CookTime cookTime) {

    public RecipeDetail(CreateRecipeRequest request){
        this(
            request.description(),
            request.difficulty(),
            request.cost(),
            request.people(),
            new CookTime(request.preparationTime(), request.chillTime(), request.cookTime())
        );
    }
}
