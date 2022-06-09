package fr.sweetiez.api.core.recipes.services;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;

public class RecipeService {

    private final RecipeReader reader;
    private final RecipeWriter writer;

    public RecipeService(RecipeReader reader, RecipeWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public Recipe createRecipe(CreateRecipeRequest request) {
        var recipe = new Recipe(request);

        return writer.save(recipe);
    }

    public Recipe addStep(CreateStepRequest request) {
        var step = new Step(request);
        var recipe = reader.findById(request.id());
        recipe.addStep(step);
        return writer.save(recipe);
    }


}
