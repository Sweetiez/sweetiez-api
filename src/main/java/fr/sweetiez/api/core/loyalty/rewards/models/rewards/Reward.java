package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

public record Reward(RewardId id, Cost cost, Product product) {

    public boolean isValid() {
        return id.isValid() && cost.isValid() && product.isValid();
    }
}
