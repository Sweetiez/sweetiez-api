package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

import java.time.LocalDate;

public record AdminSimpleOrderResponse(
        String id,
        String firstName,
        String lastName,
        LocalDate pickupDate,
        OrderStatus status,
        LocalDate createdAt) {}
