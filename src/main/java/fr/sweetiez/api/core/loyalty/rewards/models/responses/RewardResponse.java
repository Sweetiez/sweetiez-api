package fr.sweetiez.api.core.loyalty.rewards.models.responses;


import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;

public record RewardResponse(String id,
                             String name,
                             Integer cost,
                             String productId,
                             String productType,
                             String productName,
                             String productImage) {

    public RewardResponse(Reward reward) {
        this(reward.id().value().toString(),
                reward.name(),
                reward.cost().value(),
                reward.product().id(),
                reward.product().type().toString(),
                reward.product().name(),
                reward.product().image());
    }

}
