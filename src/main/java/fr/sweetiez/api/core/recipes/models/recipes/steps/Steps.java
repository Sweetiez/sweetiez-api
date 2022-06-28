package fr.sweetiez.api.core.recipes.models.recipes.steps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public record Steps(Collection<Step> steps) {

    public Steps() {
        this(Set.of());
    }

    public Steps addStep(Step step) {
        var newSteps = new ArrayList<>(steps);
        System.out.println(newSteps);
        if (steps.size() == 1 && steps.toArray()[0].equals("")) {
            newSteps = new ArrayList<>();
        }
        newSteps.add(step);
        return new Steps(newSteps);
    }
}
