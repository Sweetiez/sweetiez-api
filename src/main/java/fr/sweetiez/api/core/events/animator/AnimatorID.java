package fr.sweetiez.api.core.events.animator;

import java.util.Objects;
import java.util.UUID;

public class AnimatorID {
    private final UUID animatorId;

    public AnimatorID(UUID animatorId) {
        this.animatorId = animatorId;
    }

    public UUID getAnimatorId() {
        return animatorId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimatorID that = (AnimatorID) o;
        return Objects.equals(animatorId, that.animatorId);
    }

    public int hashCode() {
        return Objects.hash(animatorId);
    }
}


