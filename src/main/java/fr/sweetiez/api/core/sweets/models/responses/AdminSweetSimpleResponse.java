package fr.sweetiez.api.core.sweets.models.responses;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

import java.util.Collection;

public record AdminSweetSimpleResponse(
        String id,
        String name,
        double price,
        String status,
        String description,
        Collection<String> images,
        String highlight
) {


    public AdminSweetSimpleResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.states().state().toString(),
                sweet.details().description().shortContent(),
                sweet.details().images(),
                sweet.states().highlight().toString()
        );
    }
}
