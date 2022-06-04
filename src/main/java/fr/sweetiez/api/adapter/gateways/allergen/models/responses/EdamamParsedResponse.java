package fr.sweetiez.api.adapter.gateways.allergen.models.responses;

import java.util.List;

public record EdamamParsedResponse(
        String text,
        List<FoodWrapper> parsed,
        List<Object> hints,
        Object _links
) {}
