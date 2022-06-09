package fr.sweetiez.api.core.recipes.models.recipes.steps;

public record Step(StepId id,
                   Integer order,
                   String description) {

}
