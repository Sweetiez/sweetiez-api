package fr.sweetiez.api.acceptance.steps;

import fr.sweetiez.api.account.CustomerAccountRepository;
import fr.sweetiez.api.authentication.AuthenticationService;
import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.order.Order;
import fr.sweetiez.api.order.OrderRepository;
import fr.sweetiez.api.usecases.payment.Cart;
import fr.sweetiez.api.usecases.payment.CreditCard;
import fr.sweetiez.api.usecases.payment.PayCart;
import fr.sweetiez.api.usecases.payment.PaymentService;
import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSteps implements En {

    public PaymentSteps(PaymentService paymentService,
                        AuthenticationService authenticationService,
                        CustomerAccountRepository customerAccountRepository,
                        OrderRepository orderRepository)
    {
        final var payCart = new PayCart(paymentService, customerAccountRepository, orderRepository);
        var orderAttempt = new OrderAttempt();
        var creditCardBridge = new CreditCardBridge();

        And("^the credit card with the number \"([^\"]*)\" hold by \"([^\"]*)\"" +
                        " expiring on \"([^\"]*)\" with the CCV \"([^\"]*)\" is valid$",
            (String number, String holder, String date, Integer ccv) -> {
                var creditCard = new CreditCard(number, holder, date, ccv);
                creditCardBridge.from(number, holder, date, ccv);
                assertTrue(creditCard.isValid());
        });

        And("^the credit card with the number \"([^\"]*)\" hold by \"([^\"]*)\"" +
                        " expiring on \"([^\"]*)\" with the CCV \"([^\"]*)\" is not valid$",
            (String number, String holder, String date, Integer ccv) -> {
                var creditCard = new CreditCard(number, holder, date, ccv);
                creditCardBridge.from(number, holder, date, ccv);
                assertFalse(creditCard.isValid());
        });

        When("^I try to pay the cart containing \"([^\"]*)\" items for a value of \"([^\"]*)\" euros" +
                        " to receive my order the \"([^\"]*)\"$",
            (Integer articlesNumber, BigDecimal price, String date) -> {
                var creditCard = creditCardBridge.to();
                var cart = new Cart(new ArrayList<>(articlesNumber), price);
                var deliveryDate = LocalDateTime.parse(date);
                        var customer = authenticationService.currentCustomer()
                        .orElseGet(() -> new Customer(
                                "aze",
                                "Azerty",
                                "MOKUTH",
                                "azerty.mokuth@gmail.com"));


                payCart.handle(customer, creditCard, cart, deliveryDate);

                orderAttempt.setCart(cart);
                orderAttempt.setCreditCard(creditCard);
                orderAttempt.setCustomer(customer);
                orderAttempt.setDeliveryDate(deliveryDate);
        });

        Then("^the purchase is a success confirmed by a new order created$", () -> {
            Set<Order> orders = orderRepository.all();
            assertEquals(1, orders.size());
            var order = new Order(orderAttempt.customer, orderAttempt.creditCard, orderAttempt.cart, orderAttempt.deliveryDate);
            assertEquals(order, orders.iterator().next());
        });

        Then("^the purchase is a failure$", () -> {
            throw new PendingException();
        });
    }

    private static class CreditCardBridge {
        private String number;
        private String holder;
        private String expirationDate;
        private Integer ccv;

        public void from(String number, String holder, String expirationDate,Integer ccv) {
            this.number = number;
            this.holder = holder;
            this.expirationDate = expirationDate;
            this.ccv = ccv;
        }

        public CreditCard to() {
            return new CreditCard(number, holder, expirationDate, ccv);
        }
    }

    private static class OrderAttempt {

        private Customer customer;
        private CreditCard creditCard;
        private Cart cart;
        private LocalDateTime deliveryDate;

        void setCustomer(Customer customer) {
            this.customer = customer;
        }

        void setCreditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
        }

        void setCart(Cart cart) {
            this.cart = cart;
        }

        void setDeliveryDate(LocalDateTime deliveryDate) {
            this.deliveryDate = deliveryDate;
        }
    }
}
