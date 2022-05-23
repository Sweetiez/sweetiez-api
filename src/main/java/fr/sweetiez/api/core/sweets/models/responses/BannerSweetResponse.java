package fr.sweetiez.api.core.sweets.models.responses;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;



public record BannerSweetResponse(
        String id,
        String name,
        String images) {

    public BannerSweetResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.details().images().toArray()[0].toString()
        );
    }
}
