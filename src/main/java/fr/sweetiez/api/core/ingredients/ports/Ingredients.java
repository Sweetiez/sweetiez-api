package fr.sweetiez.api.core.ingredients.ports;

import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.Ingredient;

import java.util.Collection;
import java.util.Optional;

public interface Ingredients {
    Optional<Ingredient> findByName(String name);
    Collection<HealthProperty> retrieveAllHealthProperties();
    HealthProperty saveHealthProperty(HealthProperty dietProperty);
    Ingredient saveIngredient(Ingredient ingredient);
}
