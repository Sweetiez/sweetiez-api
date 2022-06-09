package fr.sweetiez.api.core.recipes.ports;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;

public interface RecipeWriter {
    Recipe save(Recipe recipe);
}
