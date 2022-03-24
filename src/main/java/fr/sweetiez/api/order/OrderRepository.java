package fr.sweetiez.api.order;

import java.util.Set;

public interface OrderRepository {
    Set<Order> all();
    void add(Order order);
}
