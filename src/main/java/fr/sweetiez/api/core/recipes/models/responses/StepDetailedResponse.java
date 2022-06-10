package fr.sweetiez.api.core.recipes.models.responses;

import fr.sweetiez.api.core.recipes.models.recipes.steps.Step;

public record StepDetailedResponse (String id,
                                    Integer order,
                                    String description)
{
    public StepDetailedResponse(Step step) {
        this(
            step.id().id().toString(),
            step.order(),
            step.description()
        );
    }
}
