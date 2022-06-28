package fr.sweetiez.api.core.loyalty.rewards.models.requests;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;

public record UpdateRewardRequest(String id,
                                  String rewardName,
                                  Integer cost,
                                  String productId,
                                  ProductType productType,
                                  State state) {

}
