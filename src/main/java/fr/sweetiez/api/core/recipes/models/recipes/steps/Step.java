package fr.sweetiez.api.core.recipes.models.recipes.steps;

import fr.sweetiez.api.core.recipes.models.requests.CreateStepRequest;
import fr.sweetiez.api.core.recipes.models.requests.UpdateStepRequest;

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

    public Step(UpdateStepRequest request) {
        this(new StepId(request.id()),
            request.order(),
            request.description()
        );
    }
}
