package fr.sweetiez.api.core.orders.ports;

import fr.sweetiez.api.core.orders.models.orders.Order;

public interface OrdersNotifier {

    void notifyCustomer(Order order);

    void notifyAdmin(Order order);
}
