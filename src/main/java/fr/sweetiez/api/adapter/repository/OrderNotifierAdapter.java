package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;
import fr.sweetiez.api.core.orders.ports.OrdersNotifier;
import fr.sweetiez.api.infrastructure.notification.email.EmailNotifier;
import fr.sweetiez.api.infrastructure.notification.email.dtos.OrderEmailDto;
import fr.sweetiez.api.infrastructure.notification.email.dtos.ProductEmailDto;

import java.math.BigDecimal;

public class OrderNotifierAdapter implements OrdersNotifier {

    private final EmailNotifier notifier;

    public OrderNotifierAdapter(EmailNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void notifyCustomer(DetailedOrderResponse order) {
        notifier.send(new OrderEmailDto(
                order.email(),
                String.format("FI-Sweets.fr - Votre commande %s", order.id()),
                order.firstName(),
                order.lastName(),
                order.id(),
                order.pickupDate().toString(),
                order.totalPrice(),
                order.products().stream()
                        .map(p -> new ProductEmailDto(
                                p.name(),
                                p.quantity(),
                                p.unitPerPackage(),
                                p.unitPrice(),
                                p.total().multiply(new BigDecimal(p.quantity()))
                                )
                        )
                        .toList(),
                order.rewardName()
                )
        );
    }

    @Override
    public void notifyAdmin(Order order) {

    }
}
