package fr.sweetiez.api.core.recipes.ports;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.Recipes;

public interface RecipeReader {

    Recipe findById(String id);

    Recipes findAll();
}
