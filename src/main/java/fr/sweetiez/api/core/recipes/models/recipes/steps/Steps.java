package fr.sweetiez.api.core.recipes.models.recipes.steps;

import java.util.Collection;
import java.util.Set;

public record Steps(Collection<Step> steps) {

    public Steps() {
        this(Set.of());
    }
}
