package fr.sweetiez.api.core.loyalty.rewards.models.requests;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;

public record CreateRewardRequest(String rewardName, Integer cost, String productId, ProductType productType) {
}
