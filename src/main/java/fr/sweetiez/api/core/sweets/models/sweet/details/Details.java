package fr.sweetiez.api.core.sweets.models.sweet.details;

import fr.sweetiez.api.core.ingredients.models.Ingredients;

import java.util.Collection;

public record Details(Description description, Flavor flavor, Collection<String> images, Ingredients ingredients, double score) {
    public boolean isValid() {
        return description != null && flavor != null && images != null && ingredients.isValid();
    }
}
