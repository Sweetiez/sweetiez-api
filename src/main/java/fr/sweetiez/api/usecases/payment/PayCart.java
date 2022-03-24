package fr.sweetiez.api.usecases.payment;

import fr.sweetiez.api.account.CustomerAccount;
import fr.sweetiez.api.account.CustomerAccountRepository;
import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.order.Order;
import fr.sweetiez.api.order.OrderRepository;

import java.time.LocalDateTime;

public class PayCart {

    private final PaymentService paymentService;
    private final CustomerAccountRepository customerAccountRepository;
    private final OrderRepository orderRepository;

    public PayCart(PaymentService paymentService, CustomerAccountRepository customerAccountRepository, OrderRepository orderRepository) {
        this.paymentService = paymentService;
        this.customerAccountRepository = customerAccountRepository;
        this.orderRepository = orderRepository;
    }

    public void handle(Customer customer, CreditCard creditCard, Cart cart, LocalDateTime deliveryDate) {
        if (cart.isValid() && this.deliveryDateIsValid(deliveryDate)) {
            // pay cart value
            if (paymentService.pay(creditCard)) {
                // create order
                var order = new Order(customer, creditCard, cart, deliveryDate);
                orderRepository.add(order);

                // update customer loyalty points
                this.updateCustomerLoyaltyPoints(customer, cart);
            }
        }
    }

    private Boolean deliveryDateIsValid(LocalDateTime deliveryDate) {
        var today = LocalDateTime.parse("2021-12-29T13:00:00").plusDays(3);
        return today.isEqual(deliveryDate) || today.isBefore(deliveryDate);
    }

    private void updateCustomerLoyaltyPoints(Customer customer, Cart cart) {
        customerAccountRepository.findById(customer.getId()).ifPresent(account -> {
            var newPointsAmount = account.getLoyaltyPoints() + cart.getPrice().longValue();
            account.setLoyaltyPoints(newPointsAmount);
            customerAccountRepository.update(account);
        });
    }
}
