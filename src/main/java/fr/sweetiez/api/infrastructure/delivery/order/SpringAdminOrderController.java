package fr.sweetiez.api.infrastructure.delivery.order;

import fr.sweetiez.api.adapter.delivery.OrderEndPoints;
import fr.sweetiez.api.core.orders.models.responses.AdminDetailedOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.AdminSimpleOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class SpringAdminOrderController {

    private final OrderEndPoints orderEndPoints;

    public SpringAdminOrderController(final OrderEndPoints orderEndPoints) {
        this.orderEndPoints = orderEndPoints;
    }

    @GetMapping
    public ResponseEntity<List<AdminSimpleOrderResponse>> getOrders() {
        return this.orderEndPoints.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailedOrderResponse> getOrderById(@PathVariable("id") String id) {
        return this.orderEndPoints.getOrder(id);
    }
}
