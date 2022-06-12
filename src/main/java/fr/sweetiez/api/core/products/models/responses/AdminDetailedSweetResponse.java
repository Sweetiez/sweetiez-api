package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.products.models.Sweet;

import java.util.Collection;
import java.util.UUID;

public record AdminDetailedSweetResponse(
        UUID id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        Collection<Ingredient> ingredients,
        ValuationResponse valuations,
        String state,
        String highlight
)
{
    public AdminDetailedSweetResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.description().content(),
                sweet.details().characteristics().flavor().name(),
                sweet.details().images(),
                sweet.ingredients(),
                new ValuationResponse(sweet.details().valuation()),
                sweet.details().characteristics().state().name(),
                sweet.details().characteristics().highlight().name()
        );
    }
}
