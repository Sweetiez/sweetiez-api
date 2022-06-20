package fr.sweetiez.api.core.loyalty.rewards.models.requests;

public record CreateRewardRequest(String rewardName, Integer cost, String productId, String productName) {
}
