package fr.sweetiez.api.core.recipes.models.recipes.steps;

import java.util.UUID;

public record StepId(UUID id) {

    public StepId() {
        this(UUID.randomUUID());
    }

    public StepId(String value) {
        this(UUID.fromString(value));
    }

}
