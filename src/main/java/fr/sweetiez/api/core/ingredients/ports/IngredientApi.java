package fr.sweetiez.api.core.ingredients.ports;

import fr.sweetiez.api.adapter.gateways.allergen.models.HealthLabel;

import java.util.Collection;

public interface IngredientApi {
    String findIdByName(String name);
    Collection<HealthLabel> getHealthLabelsOf(String ingredientId);

}
