package fr.sweetiez.api.adapter.gateways.allergen.models.responses;

public record Food(
        String foodId,
        String label,
        Object nutrients,
        String category,
        String categoryLabel,
        String image
) {}
