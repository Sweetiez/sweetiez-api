package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;

public record Reward(RewardId id, String name, Cost cost, Product product) {

    public Reward(CreateRewardRequest request) {
        this(new RewardId(),
                request.rewardName(),
                new Cost(request.cost()),
                new Product(
                        request.productId(),
                        request.productType()
                ));
    }

    public Reward(Reward reward, String name, String image) {
        this(reward.id(), name, reward.cost(),
                new Product(reward.product().id(), name, reward.product().type(), image)
        );
    }

    public boolean isValid() {
        return id.isValid() && !name.isEmpty() && cost.isValid() && product.isValid();
    }
}
