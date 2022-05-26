package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.Order;

public record OrderCreatedResponse(String orderId) {

    public OrderCreatedResponse(Order order) {
        this(order.id().value().toString());
    }
}
