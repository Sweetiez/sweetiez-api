package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

import java.time.LocalDate;

public record SimpleOrderResponse(
        String id,
        String firstName,
        String lastName,
        LocalDate pickupDate,
        OrderStatus status,
        LocalDate createdAt,
        double totalPrice) {

    public SimpleOrderResponse(Order order) {
        this(order.id().value().toString(),
                order.customerInfo().firstName(),
                order.customerInfo().lastName(),
                order.pickupDate(),
                order.status(),
                order.createdAt(),
                order.totalPrice());
    }

}
