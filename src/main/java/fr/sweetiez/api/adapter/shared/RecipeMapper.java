package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.RecipeId;
import fr.sweetiez.api.core.recipes.models.recipes.details.CookTime;
import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDetail;
import fr.sweetiez.api.core.recipes.models.recipes.details.Title;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.recipes.steps.StepId;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Steps;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeEntity;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RecipeMapper {

    public Step toDto(RecipeStepEntity entity) {
        return new Step(new StepId(entity.getId()), entity.getOrder(), entity.getDescription());
    }

    public RecipeStepEntity toEntity(Step step, UUID recipeId) {
        return new RecipeStepEntity(step.id().id(), recipeId, step.order(), step.description());
    }

    public RecipeEntity toEntity(Recipe recipe) {
        return new RecipeEntity(
                recipe.id().id(),
                recipe.title().value(),
                recipe.detail().description(),
                recipe.detail().difficulty(),
                recipe.detail().cost(),
                recipe.detail().people(),
                recipe.detail().cookTime().preparationTime(),
                recipe.detail().cookTime().chillTime(),
                recipe.detail().cookTime().cookTime(),
                recipe.images().stream()
                        .map(image -> image.isEmpty() ? image : image.concat(";"))
                        .reduce("", String::concat)
        );
    }

    public Recipe toDto(RecipeEntity entity, Collection<RecipeStepEntity> recipeSteps) {
        return new Recipe(
                new RecipeId(entity.getId()),
                new Title(entity.getTitle()),
                new RecipeDetail(
                        entity.getDescription(),
                        entity.getDifficulty(),
                        entity.getCost(),
                        entity.getPeople(),
                        new CookTime(
                            entity.getPreparationTime(),
                            entity.getChillTime(),
                            entity.getCookTime()
                        )
                ),
                List.of(entity.getImages().split(";")),
                new Steps(recipeSteps.stream().map(this::toDto).toList()));
    }
}
