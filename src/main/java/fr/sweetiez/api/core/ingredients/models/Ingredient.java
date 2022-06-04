package fr.sweetiez.api.core.ingredients.models;

import java.util.Collection;
import java.util.UUID;

public record Ingredient(UUID id, String name, Collection<HealthProperty> healthProperties) {
    public boolean isValid() {
        return healthProperties != null && name != null && !name.isEmpty() && !name.isBlank();
    }
}
