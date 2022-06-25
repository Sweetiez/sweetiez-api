package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.orders.models.orders.CustomerInfo;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.OrderId;
import fr.sweetiez.api.core.orders.models.orders.products.ProductOrder;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailEntity;
import fr.sweetiez.api.infrastructure.repository.orders.OrderEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class OrderMapper {

    public ProductOrder toDto(OrderDetailEntity entity) {
        return new ProductOrder(
                entity.getProductId().toString(),
                entity.getName(),
                entity.getProductType(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    public OrderDetailEntity toEntity(ProductOrder product, UUID orderId) {
        return new OrderDetailEntity(
                product.id(),
                orderId,
                product.productId(),
                product.name(),
                product.quantity().value(),
                product.unitPrice().unitPrice().doubleValue(),
                product.type()
        );
    }

    public Order toDto(OrderEntity entity, Collection<OrderDetailEntity> orderDetails) {
        return new Order(
                new OrderId(entity.getId()),
                new CustomerInfo(
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getPhone()
                ),
                entity.getPickupDate(),
                entity.getStatus(),
                orderDetails.stream()
                        .map(this::toDto)
                        .toList(),
                Optional.of(new CustomerId(entity.getCustomerId())),
                entity.getTotalPrice(),
                entity.getCreatedAt(),
                entity.getPaymentIntent(),
                entity.getRewardId()
        );
    }

    public OrderEntity toEntity(Order order) {
        return new OrderEntity(
                order.id().value(),
                order.customerInfo().firstName(),
                order.customerInfo().lastName(),
                order.customerInfo().email(),
                order.customerInfo().phone(),
                order.totalPrice(),
                order.status(),
                order.customerId().orElse(new CustomerId("")).value(),
                order.pickupDate(),
                order.createdAt(),
                order.paymentIntent(),
                order.rewardId()
        );
    }



}
