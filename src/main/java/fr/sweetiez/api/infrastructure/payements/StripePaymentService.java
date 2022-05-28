package fr.sweetiez.api.infrastructure.payements;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.PaymentService;
import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.SignatureVerificationFailedException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.UnhandledEventType;

public class StripePaymentService implements PaymentService {

    private final String endpointSecret;

    public StripePaymentService(String endpointSecret, String secretStripeKey) {
        this.endpointSecret = endpointSecret;
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

    public String webhookPayment(String payload, String sigHeader) throws UnhandledEventType, SignatureVerificationFailedException {
//        System.out.println(payload);
//        System.out.println(sigHeader);
        Event event;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );
        } catch (SignatureVerificationException e) {
            throw new SignatureVerificationFailedException();
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded": {
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                return paymentIntent.getId();
            }
            // ... handle other event types
            default:

                throw new UnhandledEventType();
        }
    }

    private Long convertToCents(double amount) {
        return Math.round(amount * 100);
    }
}
