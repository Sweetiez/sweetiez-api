package fr.sweetiez.api.core.orders.services;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.products.Product;
import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.AdminSimpleOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.core.orders.services.exceptions.InvalidOrderException;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.services.SweetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderService {

    private final OrdersWriter writer;

    private final OrdersReader reader;

    private final SweetService sweetService;

    private final CustomerService customerService;

    public OrderService(OrdersWriter writer, OrdersReader reader, SweetService sweetService, CustomerService customerService) {
        this.writer = writer;
        this.reader = reader;
        this.sweetService = sweetService;
        this.customerService = customerService;
    }

    public OrderCreatedResponse create(CreateOrderRequest request) throws InvalidOrderException {
        Optional<CustomerId> customerId = Optional.empty();
        try {
            customerId = Optional.of(customerService.findByEmail(request.email()).id());
        } catch (Exception e) {
            System.out.println("Customer not found");
        }
        // Retrieve the products by their ids
        var sweetProducts = getSweetProducts(request);
        var totalPrice = computeTotalPrice(sweetProducts);
        // Create the order
        var order = new Order(request, sweetProducts, customerId, totalPrice, LocalDate.now());

        // Check if the order is valid
        if (!order.isValid()) {
            throw new InvalidOrderException();
        }

        // Save the order
        return new OrderCreatedResponse(this.writer.save(order));
    }

    public List<AdminSimpleOrderResponse> getAll() {
        var orders = this.reader.findAll();
        return orders.orders().stream()
                .map(order -> new AdminSimpleOrderResponse(
                        order.id().value().toString(),
                        order.customerInfo().firstName(),
                        order.customerInfo().lastName(),
                        order.pickupDate(),
                        order.status(),
                        order.createdAt()))
                .toList();
    }

    private List<Product> getSweetProducts(CreateOrderRequest request) {
        return request.products().stream()
                .filter(productRequest -> productRequest.type() == ProductType.SWEET)
                .map(productRequest -> new TmpProcessItem(
                        this.sweetService.retrieveSweetDetails(productRequest.productId().toString()),
                        productRequest.quantity()))
                .map(item -> new Product(
                        item.sweet.id(),
                        item.sweet.name(),
                        ProductType.SWEET,
                        item.quantity,
                        item.sweet.price())
                )
                .toList();
    }

    /**
     * Computation of the total price of the order
     * @return value to pay
     */
    private double computeTotalPrice(List<Product> products) {
        return products.stream()
                .map(p -> p.unitPrice().value().doubleValue() * p.quantity().value())
                .reduce(0.0, Double::sum);
    }

    private record TmpProcessItem(DetailedSweetResponse sweet, Integer quantity) {
    }
}
