package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.orders.services.exceptions.InvalidOrderException;
import org.springframework.http.ResponseEntity;

public class OrderEndPoints {

    private final OrderService orderService;

    public OrderEndPoints(OrderService orderService) {
        this.orderService = orderService;
    }

    public ResponseEntity<OrderCreatedResponse> createOrder(CreateOrderRequest request) {
        try {
            return ResponseEntity.ok(orderService.create(request));
        } catch (InvalidOrderException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
