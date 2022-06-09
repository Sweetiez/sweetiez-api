package fr.sweetiez.api.core.recipes.models.recipes;

import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDetail;
import fr.sweetiez.api.core.recipes.models.recipes.details.Title;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Steps;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;

import java.util.Collection;
import java.util.Set;

public record Recipe(RecipeId id,
                     Title title,
                     RecipeDetail detail,
                     Collection<String> images,
                     Steps steps) {

    public Recipe(CreateRecipeRequest request) {
        this(
            new RecipeId(),
            new Title(request.title()),
            new RecipeDetail(request),
            Set.of(),
            new Steps()
        );
    }

    public Recipe addStep(Step step) {
        steps.addStep(step);
        return this;
    }
}
