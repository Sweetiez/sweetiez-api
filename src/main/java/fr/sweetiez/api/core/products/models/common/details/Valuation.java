package fr.sweetiez.api.core.products.models.common.details;

import fr.sweetiez.api.core.evaluations.models.Evaluation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public record Valuation(Collection<Evaluation> evaluations) {
    public int voters() {
        return evaluations.size();
    }

    public Double globalMark() {
        if (evaluations.isEmpty()) return 0.;

        var sum = evaluations
                .stream()
                .map(Evaluation::mark)
                .reduce(0., Double::sum);

        return sum / evaluations.size();
    }

    public Map<Integer, Integer> votes() {
        var votes = new HashMap<Integer, Integer>();

        for(int i = 5; i >= 1; i--) {
            var stars = i;
            var voters =  evaluations.stream()
                    .filter(evaluation -> evaluation.mark() == stars)
                    .toList()
                    .size();
            var ratio = voters != 0 ? evaluations.size() / voters : 0;

            votes.put(stars, ratio);
        }

        return votes;
    }
}
