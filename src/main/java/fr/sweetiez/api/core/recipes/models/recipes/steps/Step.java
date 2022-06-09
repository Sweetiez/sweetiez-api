package fr.sweetiez.api.core.recipes.models.recipes.steps;

import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;

public record Step(StepId id,
                   Integer order,
                   String description) {

    public Step(CreateStepRequest request) {
        this(
            new StepId(),
            request.order(),
            request.description()
        );
    }
}
