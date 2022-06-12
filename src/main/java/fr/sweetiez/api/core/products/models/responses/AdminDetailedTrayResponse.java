package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.Tray;

import java.util.Collection;
import java.util.UUID;

public record AdminDetailedTrayResponse(
        UUID id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        Collection<Sweet> ingredients,
        ValuationResponse valuations,
        String state,
        String highlight
)
{
    public AdminDetailedTrayResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().value().doubleValue(),
                tray.description().content(),
                tray.details().characteristics().flavor().name(),
                tray.details().images(),
                tray.sweets(),
                new ValuationResponse(tray.details().valuation()),
                tray.details().characteristics().state().name(),
                tray.details().characteristics().highlight().name()
        );
    }
}
