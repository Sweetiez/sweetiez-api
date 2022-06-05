package fr.sweetiez.api.core.trays.models.responses;

import fr.sweetiez.api.core.trays.models.tray.Tray;


public record BannerTrayResponse(
        String id,
        String name,
        String images) {

    public BannerTrayResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.details().images().toArray()[0].toString()
        );
    }
}
