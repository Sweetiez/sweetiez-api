package fr.sweetiez.api.core.trays.models.responses;

import fr.sweetiez.api.core.trays.models.tray.Tray;

import java.util.Collection;

public record SimpleTrayResponse(
        String id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        double rating
)
{
    public SimpleTrayResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().value().doubleValue(),
                tray.details().description().shortContent(),
                tray.details().flavor().name(),
                tray.details().images(),
                tray.details().score());
    }
}
