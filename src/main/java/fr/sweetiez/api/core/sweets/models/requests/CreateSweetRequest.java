package fr.sweetiez.api.core.sweets.models.requests;

import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.ingredients.models.Ingredient;

import java.math.BigDecimal;
import java.util.Collection;

public record CreateSweetRequest(
        String name,
        BigDecimal price,
        Collection<Ingredient> ingredients,
        String description,
        Flavor flavor,
        String employee) {}
