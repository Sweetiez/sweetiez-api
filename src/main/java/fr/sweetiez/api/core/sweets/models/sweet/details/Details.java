package fr.sweetiez.api.core.sweets.models.sweet.details;

import fr.sweetiez.api.core.ingredients.models.Ingredients;

public record Details(String description, Flavor flavor, String imageUrl, Ingredients ingredients) {
    public boolean isValid() {
        return description != null && flavor != null && imageUrl != null && ingredients.isValid();
    }
}
