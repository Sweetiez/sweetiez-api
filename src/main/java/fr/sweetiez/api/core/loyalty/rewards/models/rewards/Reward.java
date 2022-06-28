package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.UpdateRewardRequest;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;

public record Reward(RewardId id, String name, Cost cost, Product product, State state) {

    public Reward(CreateRewardRequest request) {
        this(new RewardId(),
                request.rewardName(),
                new Cost(request.cost()),
                new Product(
                        request.productId(),
                        request.productType()
                ),
                State.NON_PUBLISHED);
    }

    public Reward(Reward reward, String name, String image) {
        this(reward.id(), reward.name, reward.cost(),
                new Product(reward.product().id(), name, reward.product().type(), image),
                reward.state
        );
    }

    public Reward(Reward reward, State state) {
        this(reward.id(), reward.name, reward.cost(), reward.product(), state);
    }

    public Reward(UpdateRewardRequest request, Reward reward) {
        this(reward.id(),
                request.rewardName(),
                new Cost(request.cost()),
                new Product(
                        request.productId(),
                        request.productType()
                ),
                request.state()
        );
    }

    public boolean isValid() {
        return id.isValid() && !name.isEmpty() && cost.isValid() && product.isValid();
    }
}
