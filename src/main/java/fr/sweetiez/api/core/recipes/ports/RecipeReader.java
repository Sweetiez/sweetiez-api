package fr.sweetiez.api.core.recipes.ports;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.Recipes;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.services.exceptions.StepNotFoundException;

public interface RecipeReader {

    Recipe findById(String id);

    Recipes findAll();

    Step findStepById(String id) throws StepNotFoundException;
}
