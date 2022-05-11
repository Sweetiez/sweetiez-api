package fr.sweetiez.api.core.ingredients.models;

import java.util.Collection;

public record Ingredient(String name, Collection<String> allergens) {
    public boolean isValid() {
        return allergens != null && name != null && !name.isEmpty() && !name.isBlank();
    }
}
