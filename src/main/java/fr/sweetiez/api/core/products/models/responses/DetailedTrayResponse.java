package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.Name;

import java.util.Collection;
import java.util.UUID;

public record DetailedTrayResponse(
        UUID id,
        String name,
        double price,
        String description,
        Collection<String> images,
        Collection<String> sweets,
        Collection<String> diets,
        Collection<String> allergens,
        ValuationResponse valuation
)
{
    public DetailedTrayResponse(Tray tray, ValuationResponse valuation) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().value().doubleValue(),
                tray.description().content(),
                tray.details().images(),
                tray.sweets().stream().map(Sweet::name).map(Name::value).toList(),
                tray.diets(),
                tray.allergens(),
                valuation
        );
    }
}
