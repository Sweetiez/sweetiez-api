package fr.sweetiez.api.core.sweets.models.sweet.details;

import fr.sweetiez.api.core.ingredients.models.Ingredients;

import java.util.Collection;

public record Details(String description, Flavor flavor, Collection<String> images, Ingredients ingredients) {
    public boolean isValid() {
        return description != null && flavor != null && images != null && ingredients.isValid();
    }
}
