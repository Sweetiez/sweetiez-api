package fr.sweetiez.api.core.recipes.models.responses;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;

import java.util.Collection;

public record SimpleRecipeResponse(
        String id,
        String name,
        String description,
        Collection<String> images
) {

    public SimpleRecipeResponse(Recipe recipe) {
        this(
                recipe.id().id().toString(),
                recipe.title().value(),
                recipe.detail().description(),
                recipe.images()
        );
    }

}
