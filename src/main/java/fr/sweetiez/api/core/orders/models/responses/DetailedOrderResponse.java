package fr.sweetiez.api.core.orders.models.responses;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record DetailedOrderResponse(
    String id,
    String firstName,
    String lastName,
    String email,
    String phone,
    OrderStatus status,
    LocalDate pickupDate,
    LocalDate createdAt,
    double totalPrice,
    List<ProductOrderedResponse> products
) {

    public DetailedOrderResponse(Order order) {
        this(order.id().value().toString(),
                order.customerInfo().firstName(),
                order.customerInfo().lastName(),
                order.customerInfo().email(),
                order.customerInfo().phone(),
                order.status(),
                order.pickupDate(),
                order.createdAt(),
                order.totalPrice(),
                order.products().stream()
                        .map(product -> new ProductOrderedResponse(
                                product.name(),
                                product.quantity().value()
                        ))
                        .toList()
        );
    }

}

