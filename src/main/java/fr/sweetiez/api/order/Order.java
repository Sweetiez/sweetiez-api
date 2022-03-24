package fr.sweetiez.api.order;

import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.usecases.payment.Cart;
import fr.sweetiez.api.usecases.payment.CreditCard;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private final Customer customer;
    private final CreditCard creditCard;
    private final Cart cart;
    private final LocalDateTime deliveryDate;

    public Order(Customer customer, CreditCard creditCard, Cart cart, LocalDateTime deliveryDate) {
        this.customer = customer;
        this.creditCard = creditCard;
        this.cart = cart;
        this.deliveryDate = deliveryDate;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) && Objects.equals(creditCard, order.creditCard) && Objects.equals(cart, order.cart);
    }

    public int hashCode() {
        return Objects.hash(customer, creditCard, cart);
    }

    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", creditCard=" + creditCard +
                ", cart=" + cart +
                '}';
    }
}
