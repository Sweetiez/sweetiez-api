package fr.sweetiez.api.core.products.models.responses;


import fr.sweetiez.api.core.products.models.Sweet;

import java.util.Collection;
import java.util.UUID;

public record AdminSimpleProductResponse(
        UUID id,
        String name,
        double price,
        String status,
        String description,
        Collection<String> images,
        String highlight
) {
    public AdminSimpleProductResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.details().characteristics().state().toString(),
                sweet.description().shortContent(),
                sweet.details().images(),
                sweet.details().characteristics().highlight().toString()
        );
    }
}
