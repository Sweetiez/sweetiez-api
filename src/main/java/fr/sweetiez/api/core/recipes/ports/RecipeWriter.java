package fr.sweetiez.api.core.recipes.ports;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.requests.RemoveStepRequest;

public interface RecipeWriter {
    Recipe save(Recipe recipe);

    void removeStep(RemoveStepRequest request);
}
