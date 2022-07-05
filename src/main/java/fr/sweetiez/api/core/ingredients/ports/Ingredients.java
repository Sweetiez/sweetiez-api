package fr.sweetiez.api.core.ingredients.ports;

import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.Ingredient;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Ingredients {
    Optional<Ingredient> findByName(String name);
    Optional<Ingredient> findById(UUID id);
    Collection<HealthProperty> retrieveAllHealthProperties();
    HealthProperty saveHealthProperty(HealthProperty dietProperty);
    Ingredient saveIngredient(Ingredient ingredient);
    Collection<Ingredient> findAllById(Collection<UUID> ingredients);
    Collection<Ingredient> findAll();
    void delete(UUID id);
}
