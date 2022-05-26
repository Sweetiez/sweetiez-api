package fr.sweetiez.api.infrastructure.delivery.order;

import fr.sweetiez.api.adapter.delivery.OrderEndPoints;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class SpringOrderController {

    private final OrderEndPoints orderEndPoints;

    public SpringOrderController(OrderEndPoints orderEndPoints) {
        this.orderEndPoints = orderEndPoints;
    }

    @PostMapping
    public ResponseEntity<OrderCreatedResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return orderEndPoints.createOrder(request);
    }

}