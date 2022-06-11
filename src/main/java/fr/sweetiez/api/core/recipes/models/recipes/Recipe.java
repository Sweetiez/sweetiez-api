package fr.sweetiez.api.core.recipes.models.recipes;

import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDetail;
import fr.sweetiez.api.core.recipes.models.recipes.details.Title;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Steps;
import fr.sweetiez.api.core.recipes.models.requests.CreateRecipeRequest;

import java.util.*;
import java.util.stream.Collectors;

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

    public Recipe(Recipe recipe, Steps steps) {
        this(
            recipe.id(),
            recipe.title(),
            recipe.detail(),
            recipe.images(),
            steps
        );
    }

    public Recipe addStep(Step step) {
        return new Recipe(this, steps.addStep(step));
    }

    public Recipe changeStepsOrder(List<Step> steps) {
        return new Recipe(this, new Steps(steps));
    }

    public Recipe sortSteps() {
        var stepList = new ArrayList<>(steps.steps());
        stepList.sort(Comparator.comparing(Step::order));
        return new Recipe(this, new Steps(stepList));
    }

    public Recipe addImage(String imageUrl) {
        var imageList = new ArrayList<>(images);
        if (images.size() == 1 && images.toArray()[0].equals("")) {
            imageList = new ArrayList<>();
        }
        imageList.add(imageUrl);
        return new Recipe(this.id(), this.title(), this.detail(), imageList, this.steps());
    }

    public Recipe deleteImage(String imageUrl) {
        var updatedImages = images.stream()
                .filter(image -> !image.equals(imageUrl))
                .collect(Collectors.toSet());

        return new Recipe(this.id(), this.title(), this.detail(), updatedImages, this.steps());
    }
}
