package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import fr.sweetiez.api.core.evaluations.models.VoterResponse;
import fr.sweetiez.api.core.products.models.common.details.Valuation;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record ValuationResponse(
        Double mark,
        int voters,
        Map<Integer, Integer> votes,
        Collection<EvaluationResponse> comments)
{
    public ValuationResponse(Valuation valuation) {
        this(
                valuation.globalMark(),
                valuation.voters(),
                valuation.votes(),
                valuation.evaluations()
                        .stream()
                        .map(eval -> new EvaluationResponse(
                                UUID.fromString(eval.id().value()),
                                eval.comment(),
                                new VoterResponse(
                                        UUID.fromString(eval.voter().id().value()),
                                        eval.voter().firstName()
                                ),
                                eval.subject(),
                                eval.mark(),
                                eval.date()))
                        .toList()
        );
    }
}
