package fr.sweetiez.api.core.orders.models.requests;

import java.util.Collection;

public record CreateOrderRequest(String firstName,
                                 String lastName,
                                 String email,
                                 String phone,
                                 String pickupDate,
                                 Collection<ProductOrderRequest> products,
                                 String rewardId) {}
