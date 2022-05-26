package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.AdminDetailedOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.AdminSimpleOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.orders.services.exceptions.InvalidOrderException;
import fr.sweetiez.api.core.orders.services.exceptions.OrderNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    public ResponseEntity<List<AdminSimpleOrderResponse>> getOrders() {
        return ResponseEntity.ok().body(orderService.getAll());
    }

    public ResponseEntity<AdminDetailedOrderResponse> getOrder(String id) {
        try {
            return ResponseEntity.ok().body(orderService.getById(id));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
