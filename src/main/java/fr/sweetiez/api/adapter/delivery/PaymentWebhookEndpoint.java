package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import fr.sweetiez.api.core.orders.models.orders.PaymentService;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.orders.services.exceptions.OrderNotFoundException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.SignatureVerificationFailedException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.UnhandledEventType;
import org.springframework.http.ResponseEntity;

public class PaymentWebhookEndpoint {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentWebhookEndpoint(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public ResponseEntity<String> webhook(String payload, String sigHeader) {
        try {
            var paymentId = paymentService.webhookPayment(payload, sigHeader);

            try {
                var order = orderService.confirmPayment(paymentId);
                if (order.status().equals(OrderStatus.PAID.toString())) {
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.status(418).body("Fail to confirm order");
            } catch (OrderNotFoundException e) {
                System.out.println("Order not found");
                return ResponseEntity.status(404).body("Order not found");
            }
        } catch (SignatureVerificationFailedException e) {
            // Invalid signature
            System.out.println("Invalid signature");
            return ResponseEntity.badRequest().body("Invalid signature");
        } catch (UnhandledEventType e) {
            // Unhandled event type
            System.out.println("UnhandledEventType");
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            // Invalid payload
            System.out.println("Invalid payload");
            return ResponseEntity.badRequest().body("Invalid payload");
        }

    }

}
