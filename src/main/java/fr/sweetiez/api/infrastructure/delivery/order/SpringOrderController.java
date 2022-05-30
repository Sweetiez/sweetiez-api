package fr.sweetiez.api.infrastructure.delivery.order;

import fr.sweetiez.api.adapter.delivery.OrderEndPoints;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import fr.sweetiez.api.core.orders.models.responses.SimpleOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class SpringOrderController {

    private final OrderEndPoints orderEndPoints;

    public SpringOrderController(OrderEndPoints orderEndPoints) {
        this.orderEndPoints = orderEndPoints;
    }

    @PostMapping
    public ResponseEntity<OrderCreatedResponse> createOrder(@RequestBody CreateOrderRequest request) {
        System.out.println(request);
        return orderEndPoints.createOrder(request);
    }

    @GetMapping("/me")
    public ResponseEntity<List<DetailedOrderResponse>> getClientOrders(Authentication authentication) {
        return orderEndPoints.retrieveClientOrder(authentication.getName());
    }

}
