package fr.sweetiez.api.core.recipes.models.requests;

import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDifficulty;

public record CreateRecipeRequest(String title,
                                  String description,
                                  RecipeDifficulty difficulty,
                                  Integer cost,
                                  Integer people,
                                  Integer preparationTime,
                                  Integer chillTime,
                                  Integer cookTime
) {


}
