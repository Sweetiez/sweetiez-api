package fr.sweetiez.api.core.recipes.ports;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;

public interface RecipeReader {

    Recipe findById(String id);
}
