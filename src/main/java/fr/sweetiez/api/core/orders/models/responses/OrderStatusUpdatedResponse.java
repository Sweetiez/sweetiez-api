package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.Order;

public record OrderStatusUpdatedResponse(String orderId, String status) {

    public OrderStatusUpdatedResponse(Order order) {
        this(order.id().value().toString(), order.status().name());
    }

}
