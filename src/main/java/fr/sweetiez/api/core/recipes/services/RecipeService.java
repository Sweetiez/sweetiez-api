package fr.sweetiez.api.core.recipes.services;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.Recipes;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.requests.ChangeStepsOrderRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;
import fr.sweetiez.api.core.recipes.services.exceptions.InvalidRecipeException;
import fr.sweetiez.api.core.recipes.services.exceptions.RecipeNotFoundException;

import java.util.NoSuchElementException;
import java.util.UUID;

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
        var updatedRecipe = recipe.addStep(step);
        return writer.save(updatedRecipe);
    }


    public Recipes retrieveAll() {
        return reader.findAll();
    }

    public Recipe retrieveById(String id) throws InvalidRecipeException, RecipeNotFoundException {
        try {
            UUID.fromString(id);
            return reader.findById(id);
        }
        catch (IllegalArgumentException exception) {
            throw new InvalidRecipeException();
        } catch (NoSuchElementException exception) {
            throw new RecipeNotFoundException();
        }
    }

    public Recipe changeStepsOrder(ChangeStepsOrderRequest request) throws RecipeNotFoundException, InvalidRecipeException {
        System.out.println(request);
        var recipe = retrieveById(request.recipeId());
        var steps =request.steps().stream()
                .map(Step::new)
                .toList();
        var updatedRecipe = recipe.changeStepsOrder(steps);
        return writer.save(updatedRecipe);
    }
}
