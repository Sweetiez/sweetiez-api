package fr.sweetiez.api.core.loyalty.points.models.requests;

import fr.sweetiez.api.core.orders.models.orders.Order;

public record AddLoyaltyPointsRequest(String customerId, Double orderPrice) {

    public AddLoyaltyPointsRequest(Order order) {
        this(
            order.customerId().isPresent() ? order.customerId().get().value() : null,
            order.totalPrice()
        );
    }

}
