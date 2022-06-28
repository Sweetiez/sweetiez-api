package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Cost;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Product;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.RewardId;
import fr.sweetiez.api.infrastructure.repository.reward.RewardEntity;

public class RewardMapper {

    public RewardEntity toEntity(Reward reward) {
        return new RewardEntity(
            reward.id().value(),
            reward.name(),
            reward.cost().value(),
            reward.product().id(),
            reward.product().type(),
                reward.state()
        );
    }

    public Reward toDto(RewardEntity entity) {
        return new Reward(
            new RewardId(entity.getId()),
            entity.getRewardName(),
            new Cost(entity.getCost()),
            new Product(entity.getProductId(), entity.getProductType()),
            entity.getState()
        );
    }

}
