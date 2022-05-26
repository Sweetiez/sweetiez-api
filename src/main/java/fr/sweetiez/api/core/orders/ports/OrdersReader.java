package fr.sweetiez.api.core.orders.ports;

import fr.sweetiez.api.core.orders.models.orders.Orders;

public interface OrdersReader {
    Orders findAll();
}
