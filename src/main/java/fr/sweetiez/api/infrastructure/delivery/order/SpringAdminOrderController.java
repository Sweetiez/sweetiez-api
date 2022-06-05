package fr.sweetiez.api.infrastructure.delivery.order;

import fr.sweetiez.api.adapter.delivery.order.OrderEndPoints;
import fr.sweetiez.api.core.orders.models.requests.UpdateOrderStatus;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderStatusUpdatedResponse;
import fr.sweetiez.api.core.orders.models.responses.SimpleOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class SpringAdminOrderController {

    private final OrderEndPoints orderEndPoints;

    public SpringAdminOrderController(final OrderEndPoints orderEndPoints) {
        this.orderEndPoints = orderEndPoints;
    }

    @GetMapping
    public ResponseEntity<List<DetailedOrderResponse>> getOrders() {
        return this.orderEndPoints.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedOrderResponse> getOrderById(@PathVariable("id") String id) {
        return this.orderEndPoints.getOrder(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusUpdatedResponse> updateOrderStatus(@PathVariable("id") String id, @RequestBody UpdateOrderStatus request) {
        return this.orderEndPoints.updateOrderStatus(id, request);
    }
}
