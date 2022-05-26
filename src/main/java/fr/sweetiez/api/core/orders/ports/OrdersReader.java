package fr.sweetiez.api.core.orders.ports;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.Orders;
import fr.sweetiez.api.core.orders.models.responses.AdminDetailedOrderResponse;

import java.util.Optional;

public interface OrdersReader {
    Orders findAll();
    Optional<Order> findById(String id);
}
