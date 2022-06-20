package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;

import java.util.Collection;
import java.util.UUID;

public record DetailedSweetResponse(
        UUID id,
        String name,
        double price,
        double packagedPrice,
        int unitPerPackage,
        String description,
        Collection<String> images,
        Collection<String> ingredients,
        Collection<String> diets,
        Collection<String> allergens,
        ValuationResponse valuation,
        Highlight highlight,
        Flavor flavor
)
{
    public DetailedSweetResponse(Sweet sweet, ValuationResponse valuation) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().unitPrice().doubleValue(),
                sweet.price().packaged().doubleValue(),
                sweet.price().unitPerPackage(),
                sweet.description().content(),
                sweet.details().images(),
                sweet.ingredients().stream().map(Ingredient::name).toList(),
                sweet.diets(),
                sweet.allergens(),
                valuation,
                sweet.details().characteristics().highlight(),
                sweet.details().characteristics().flavor()
        );
    }
}
