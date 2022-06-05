package fr.sweetiez.api.core.trays.models.responses;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.trays.models.tray.Tray;

import java.util.Collection;

public record AdminDetailedTrayResponse(
        String id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        Collection<Sweet> sweets,
        double rating,
        String state,
        String highlight
)
{
    public AdminDetailedTrayResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().value().doubleValue(),
                tray.details().description().content(),
                tray.details().flavor().name(),
                tray.details().images(),
                tray.details().sweets(),
                tray.details().score(),
                tray.states().state().name(),
                tray.states().highlight().name()
        );
    }
}
