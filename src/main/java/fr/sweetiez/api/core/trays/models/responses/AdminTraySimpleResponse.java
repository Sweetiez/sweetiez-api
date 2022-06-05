package fr.sweetiez.api.core.trays.models.responses;

import fr.sweetiez.api.core.trays.models.tray.Tray;

import java.util.Collection;

public record AdminTraySimpleResponse(
        String id,
        String name,
        double price,
        String status,
        String description,
        Collection<String> images,
        String highlight
) {


    public AdminTraySimpleResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().value().doubleValue(),
                tray.states().state().toString(),
                tray.details().description().shortContent(),
                tray.details().images(),
                tray.states().highlight().toString()
        );
    }
}
