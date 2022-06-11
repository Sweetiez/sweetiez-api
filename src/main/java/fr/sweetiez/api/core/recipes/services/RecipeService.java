package fr.sweetiez.api.core.recipes.services;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.Recipes;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.requests.*;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;
import fr.sweetiez.api.core.recipes.services.exceptions.InvalidRecipeException;
import fr.sweetiez.api.core.recipes.services.exceptions.RecipeNotFoundException;
import fr.sweetiez.api.core.recipes.services.exceptions.StepNotFoundException;

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

    public Recipe removeStep(RemoveStepRequest request) throws RecipeNotFoundException, InvalidRecipeException, StepNotFoundException {
        var step = reader.findStepById(request.stepId());
        writer.removeStep(request);
        return retrieveById(request.recipeId());
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
        var recipe = retrieveById(request.recipeId());
        var steps =request.steps().stream()
                .map(Step::new)
                .toList();
        var updatedRecipe = recipe.changeStepsOrder(steps);
        return writer.save(updatedRecipe);
    }

    public Recipe addImage(String recipeId, String imageUrl) throws RecipeNotFoundException, InvalidRecipeException {
        var recipe = retrieveById(recipeId);
        var updatedRecipe = recipe.addImage(imageUrl);
        return writer.save(updatedRecipe);
    }

    public Recipe deleteImage(String recipeId, String imageUrl) throws RecipeNotFoundException, InvalidRecipeException {
        var recipe = retrieveById(recipeId);
        var updatedRecipe = recipe.deleteImage(imageUrl);
        return writer.save(updatedRecipe);
    }

    public Recipe publish(PublishRecipeRequest request) throws RecipeNotFoundException, InvalidRecipeException {
        var recipe = retrieveById(request.id());
        var updatedRecipe = recipe.publish();
        return writer.save(updatedRecipe);
    }

    public Recipe unPublish(UnPublishRecipeRequest request) throws RecipeNotFoundException, InvalidRecipeException {
        var recipe = retrieveById(request.id());
        var updatedRecipe = recipe.unPublish();
        return writer.save(updatedRecipe);
    }
}
