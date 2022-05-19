package fr.sweetiez.api.core.sweets.models.responses;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

import java.util.Collection;

public record AdminDetailedSweetResponse(
        String id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        Collection<Ingredient> ingredients,
        double rating,
        String state,
        String highlight
)
{
    public AdminDetailedSweetResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.details().description().content(),
                sweet.details().flavor().name(),
                sweet.details().images(),
                sweet.details().ingredients().content(),
                sweet.details().score(),
                sweet.states().state().name(),
                sweet.states().highlight().name()
        );
    }
}
