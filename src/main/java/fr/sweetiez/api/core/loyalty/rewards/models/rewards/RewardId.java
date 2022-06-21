package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

import java.util.UUID;

public record RewardId(UUID value) {

    public RewardId() {
        this(UUID.randomUUID());
    }

    public RewardId(String value) {
        this(UUID.fromString(value));
    }

    public boolean isValid() {
        return value != null && !value.toString().isEmpty();
    }
}
