package fr.sweetiez.api.core.orders.ports;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;

public interface OrdersNotifier {

    void notifyCustomer(DetailedOrderResponse order);

    void notifyAdmin(Order order);
}
