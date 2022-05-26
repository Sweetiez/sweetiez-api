package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record AdminDetailedOrderResponse(
    String id,
    String firstName,
    String lastName,
    String email,
    String phone,
    OrderStatus status,
    LocalDate pickupDate,
    LocalDate createdAt,
    double totalPrice,
    List<AdminProductOrderResponse> products
) {
}

