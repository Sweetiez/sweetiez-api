package fr.sweetiez.api.core.orders.models.requests;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

public record UpdateOrderStatus(OrderStatus status) {
}
