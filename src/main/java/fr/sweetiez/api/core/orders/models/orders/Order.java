package fr.sweetiez.api.core.orders.models.orders;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.orders.models.orders.products.ProductOrder;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public record Order(OrderId id,
                    CustomerInfo customerInfo,
                    LocalDate pickupDate,
                    OrderStatus status,
                    Collection<ProductOrder> products,
                    Optional<CustomerId> customerId,
                    double totalPrice,
                    LocalDate createdAt,
                    String paymentIntent,

                    String rewardId
) {

    public Order(CreateOrderRequest request, Collection<ProductOrder> products, Optional<CustomerId> optionalCustomerId, double totalPrice, LocalDate createdAt) {
        this(
                new OrderId(),
                new CustomerInfo(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.phone()
                ),
                LocalDate.parse(request.pickupDate()),
                OrderStatus.CREATED,
                products,
                optionalCustomerId,
                totalPrice,
                createdAt,
                "",
                request.rewardId()
        );
    }

    public Order(OrderId id, CustomerInfo customerInfo, LocalDate pickupDate,
                 OrderStatus status, LocalDate createdAt, double totalPrice,
                 Collection<ProductOrder> products, Optional<CustomerId> optionalCustomerId,
                 String paymentIntent, String rewardId) {
        this(id, customerInfo, pickupDate, status, products, optionalCustomerId, totalPrice, createdAt, paymentIntent, rewardId);
    }

    public Order(Order order, Collection<ProductOrder> products) {
        this(order.id, order.customerInfo, order.pickupDate, order.status, products, order.customerId, order.totalPrice, order.createdAt, order.paymentIntent, order.rewardId);
    }


    public boolean isValid() {
        return this.id.value() != null
                && this.customerInfo.isValid()
                && this.pickupDate != null
                && this.status != null
                && !this.products.isEmpty()
                && this.products.stream().allMatch(ProductOrder::isValid);
    }
}
