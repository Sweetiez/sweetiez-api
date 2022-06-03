package fr.sweetiez.api.core.recipes.models.recipes;

import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDetail;
import fr.sweetiez.api.core.recipes.models.recipes.details.Title;
import fr.sweetiez.api.core.recipes.models.recipes.steps.Steps;

import java.util.Collection;

public record Recipe(RecipeId id,
                     Title title,
                     RecipeDetail detail,
                     Collection<String> images,
                     Steps steps) {

}
