package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;

public record Reward(RewardId id, String name, Cost cost, Product product) {

    public Reward(CreateRewardRequest request) {
        this(new RewardId(request.productId()),
                request.rewardName(),
                new Cost(request.cost()),
                new Product(request.productId(), request.productName()));
    }
    public boolean isValid() {
        return id.isValid() && !name.isEmpty() && cost.isValid() && product.isValid();
    }
}
