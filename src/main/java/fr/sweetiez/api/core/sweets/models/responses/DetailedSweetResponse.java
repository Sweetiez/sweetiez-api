package fr.sweetiez.api.core.sweets.models.responses;

import fr.sweetiez.api.core.comments.models.Comment;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

import java.util.Collection;

public record DetailedSweetResponse(
        String id,
        String name,
        double price,
        String description,
        Collection<String> images,
        double rating,
        Collection<Comment> comments
)
{
    public DetailedSweetResponse(Sweet sweet, Collection<Comment> comments) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.details().description().content(),
                sweet.details().images(),
                sweet.details().score(),
                comments
        );
    }
}
