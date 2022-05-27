package fr.sweetiez.api.core.orders.models.orders;

import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;

public interface PaymentService {

    PaymentIntentResponse createPaymentIntent(Order order) throws PaymentIntentException;

}
