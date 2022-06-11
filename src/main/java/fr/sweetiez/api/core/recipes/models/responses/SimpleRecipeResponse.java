package fr.sweetiez.api.core.recipes.models.responses;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;

public record SimpleRecipeResponse(
        String id,
        String name,
        String description
) {

    public SimpleRecipeResponse(Recipe recipe) {
        this(
                recipe.id().id().toString(),
                recipe.title().value(),
                recipe.detail().description()
        );
    }

}
