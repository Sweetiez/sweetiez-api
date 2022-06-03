package fr.sweetiez.api.core.recipes.models.recipes.details;

public record RecipeDetail(String description,
                           RecipeDifficulty difficulty,
                           Integer cost,
                           Integer people,
                           CookTime cookTime) {
}
