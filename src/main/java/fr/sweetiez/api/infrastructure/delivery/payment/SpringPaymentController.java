package fr.sweetiez.api.infrastructure.delivery.payment;

import fr.sweetiez.api.adapter.delivery.order.OrderEndPoints;
import fr.sweetiez.api.adapter.delivery.payment.PaymentWebhookEndpoint;
import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class SpringPaymentController {

    private final PaymentWebhookEndpoint paymentEndpoint;
    private final OrderEndPoints orderEndPoint;

    public SpringPaymentController(final PaymentWebhookEndpoint paymentEndpoint, final OrderEndPoints orderEndPoint) {
        this.paymentEndpoint = paymentEndpoint;
        this.orderEndPoint = orderEndPoint;
    }

    @PostMapping("/intent/{orderId}")
    public ResponseEntity<PaymentIntentResponse> payOrder(@PathVariable String orderId) {
        return orderEndPoint.payOrder(orderId);
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> paidSuccessful(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return paymentEndpoint.webhook(payload, sigHeader);
    }

}
