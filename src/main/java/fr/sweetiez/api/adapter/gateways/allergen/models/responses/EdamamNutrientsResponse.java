package fr.sweetiez.api.adapter.gateways.allergen.models.responses;

import java.util.Collection;

public record EdamamNutrientsResponse (
        String uri,
        Integer calories,
        Double totalWeight,
        Collection<Object> dietLabels,
        Collection<String> healthLabels,
        Collection<Object> cautions,
        Object totalNutrients,
        Object totalDaily,
        Collection<Object> ingredients
) {}
