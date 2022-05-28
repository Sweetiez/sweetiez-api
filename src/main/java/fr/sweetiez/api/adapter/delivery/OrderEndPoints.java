package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.*;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.orders.services.exceptions.InvalidOrderException;
import fr.sweetiez.api.core.orders.services.exceptions.OrderNotFoundException;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;
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

    public ResponseEntity<List<DetailedOrderResponse>> retrieveClientOrder(String clientEmail) {
        return ResponseEntity.ok().body(orderService.retrieveClientOrders(clientEmail));
    }

    public ResponseEntity<List<SimpleOrderResponse>> getOrders() {
        return ResponseEntity.ok().body(orderService.getAll());
    }

    public ResponseEntity<DetailedOrderResponse> getOrder(String id) {
        try {
            return ResponseEntity.ok().body(orderService.getById(id));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<PaymentIntentResponse> payOrder(String orderId) {
        try {
            var paymentIntent = orderService.paymentIntent(orderId);
            return ResponseEntity.ok().body(paymentIntent);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PaymentIntentException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Deprecated since payment confirmation is managed by webhooks
     * @param orderId order id
     * @return the new status and the order id
     */
    @Deprecated
    public ResponseEntity<OrderStatusUpdatedResponse> orderPaidSuccessfully(String orderId) {
        try {
            var orderStatus = orderService.updateOrderStatus(orderId, OrderStatus.PAID);
            return ResponseEntity.ok().body(orderStatus);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
