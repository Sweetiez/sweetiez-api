package fr.sweetiez.api.core.dashboard.models.dashboard;

public record Informations(Integer accounts,
                           Integer publishedRecipes,
                           Integer publishedSweets,
                           Integer publishedTrays,
                           double monthlySales
) {
}
