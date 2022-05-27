package fr.sweetiez.api.infrastructure.payements;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.PaymentService;
import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;

public class StripePaymentService implements PaymentService {


    public StripePaymentService(String secretStripeKey) {
        Stripe.apiKey = secretStripeKey;
    }

    public PaymentIntentResponse createPaymentIntent(Order order) throws PaymentIntentException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(convertToCents(order.totalPrice()))
                        .setDescription(String.format("Order #%s | client_email: %s", order.id(), order.customerInfo().email()))
                        .setCurrency("eur")
                        .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods
                                    .builder()
                                    .setEnabled(true)
                                    .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new PaymentIntentResponse(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            throw new PaymentIntentException();
        }
    }

    private Long convertToCents(double amount) {
        return Math.round(amount * 100);
    }
}
