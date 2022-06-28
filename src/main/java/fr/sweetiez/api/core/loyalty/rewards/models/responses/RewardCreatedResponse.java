package fr.sweetiez.api.core.loyalty.rewards.models.responses;

import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;

public record RewardCreatedResponse(String id, String name) {

    public RewardCreatedResponse(Reward reward) {
        this(reward.id().value().toString(), reward.name());
    }

}
