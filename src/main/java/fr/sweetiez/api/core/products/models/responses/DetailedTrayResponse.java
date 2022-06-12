package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import fr.sweetiez.api.core.sweets.models.responses.Evaluation;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

import java.util.Collection;

public record DetailedTrayResponse(
        String id,
        String name,
        double price,
        String description,
        Collection<String> images,
        Evaluation evaluation,
        Collection<EvaluationResponse> comments
)
{
    public DetailedTrayResponse(Sweet sweet, Evaluation evaluation, Collection<EvaluationResponse> comments) {
        this(
                sweet.id().value(),
                sweet.name().value(),
                sweet.price().value().doubleValue(),
                sweet.details().description().content(),
                sweet.details().images(),
                evaluation,
                comments
        );
    }
}
