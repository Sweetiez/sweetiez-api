package fr.sweetiez.api.core.orders.models.orders;

import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.SignatureVerificationFailedException;
import fr.sweetiez.api.infrastructure.delivery.payment.exceptions.UnhandledEventType;

public interface PaymentService {

    PaymentIntentResponse createPaymentIntent(Order order) throws PaymentIntentException;

    String webhookPayment(String payload, String sigHeader) throws UnhandledEventType, SignatureVerificationFailedException;
}
