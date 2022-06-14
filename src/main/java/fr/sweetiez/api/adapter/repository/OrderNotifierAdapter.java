package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.ports.OrdersNotifier;
import fr.sweetiez.api.infrastructure.notification.email.EmailNotifier;
import fr.sweetiez.api.infrastructure.notification.email.OrderEmailDto;

public class OrderNotifierAdapter implements OrdersNotifier {

    private final EmailNotifier notifier;

    public OrderNotifierAdapter(EmailNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void notifyCustomer(Order order) {
        notifier.send(new OrderEmailDto(
                order.customerInfo().email(),
                String.format("FI-Sweets.fr - Votre commande %s", order.id().value().toString()),
                order.customerInfo().firstName(),
                order.customerInfo().lastName(),
                order.id().value().toString(),
                order.totalPrice()));
    }

    @Override
    public void notifyAdmin(Order order) {

    }
}
