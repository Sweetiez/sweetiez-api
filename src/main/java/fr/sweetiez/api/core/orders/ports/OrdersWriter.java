package fr.sweetiez.api.core.orders.ports;

import fr.sweetiez.api.core.orders.models.orders.Order;

public interface OrdersWriter {
    Order save(Order order);
}
