package fr.sweetiez.api.core.dashboard.models.dashboard;

import fr.sweetiez.api.core.orders.models.orders.Order;
import java.time.LocalDate;

public record SimpleOrder(String email, LocalDate pickupDate, double total) {

    public SimpleOrder(Order order) {
        this(order.customerInfo().email(), order.pickupDate(), order.totalPrice());
    }

}
