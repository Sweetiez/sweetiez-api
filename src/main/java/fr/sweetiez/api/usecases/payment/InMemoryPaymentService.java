package fr.sweetiez.api.usecases.payment;

public class InMemoryPaymentService implements PaymentService {

    public boolean pay(CreditCard creditCard) {
        return creditCard.isValid();
    }
}
