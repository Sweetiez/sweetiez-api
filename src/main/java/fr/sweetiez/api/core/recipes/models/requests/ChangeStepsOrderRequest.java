package fr.sweetiez.api.core.recipes.models.requests;

import java.util.List;

public record ChangeStepsOrderRequest(String recipeId, List<UpdateStepRequest> steps) {
}
