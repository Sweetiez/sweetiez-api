package fr.sweetiez.api.adapter.gateways.allergen.models.requests;

import java.util.Collection;

public record IngredientNutritionRequest(Collection<IngredientNutrition> ingredients) { }
