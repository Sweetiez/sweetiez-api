package fr.sweetiez.api.core.sweets.models.requests;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;

import java.math.BigDecimal;
import java.util.Collection;

public record UpdateSweetRequest(
        String id,
        String name,
        BigDecimal price,
        String description,
        Collection<String> images,
        Collection<Ingredient> ingredients,
        String highlight,
        String state,
        Flavor flavor,
        double rating
) {
}
