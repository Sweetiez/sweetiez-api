package fr.sweetiez.api.core.ingredients.models;

import java.util.Collection;

public record Ingredients(Collection<Ingredient> content) {
    public boolean isValid() {
        return content != null;
//        return content.stream().allMatch(Ingredient::isValid);
    }
}
