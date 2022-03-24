package fr.sweetiez.api.order;

import java.util.LinkedHashSet;
import java.util.Set;

public class InMemoryOrderRepository implements OrderRepository {

    private final Set<Order> orders = new LinkedHashSet<>();

    public Set<Order> all() {
        return orders;
    }

    public void add(Order order) {
        orders.add(order);
    }
}
