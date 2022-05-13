package fr.sweetiez.api.core.sweets.models.responses;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

import java.util.Collection;

public record SimpleSweetResponse(
        String id,
        String name,
        double price,
        String description,
        Collection<String> images,
        double rating
)
{
    public SimpleSweetResponse(Sweet sweet) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                getShortDescription(sweet.details().description()),
                sweet.details().images(),
                sweet.details().score());
    }

    private static String getShortDescription(String description) {
        return description.length() > 100 ? description.substring(0, 100) + "..." : description;
    }
}
