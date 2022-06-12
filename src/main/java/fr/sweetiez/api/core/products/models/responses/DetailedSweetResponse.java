package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.products.models.Sweet;

import java.util.Collection;
import java.util.UUID;

public record DetailedSweetResponse(
        UUID id,
        String name,
        double price,
        String description,
        Collection<String> images,
        Collection<String> ingredients,
        Collection<String> diets,
        Collection<String> allergens,
        ValuationResponse valuation
)
{
    public DetailedSweetResponse(Sweet sweet, ValuationResponse valuation) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.description().content(),
                sweet.details().images(),
                sweet.ingredients().stream().map(Ingredient::name).toList(),
                sweet.diets(),
                sweet.allergens(),
                valuation
        );
    }
}
