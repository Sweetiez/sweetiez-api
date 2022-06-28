package fr.sweetiez.api.core.orders.services;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.loyalty.points.models.requests.AddLoyaltyPointsRequest;
import fr.sweetiez.api.core.loyalty.points.services.LoyaltyPointService;
import fr.sweetiez.api.core.loyalty.points.services.exceptions.InvalidLoyaltyPointsException;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import fr.sweetiez.api.core.orders.models.orders.PaymentService;
import fr.sweetiez.api.core.orders.models.orders.products.ProductOrder;
import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.orders.models.requests.CreateOrderRequest;
import fr.sweetiez.api.core.orders.models.requests.ProductOrderRequest;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderCreatedResponse;
import fr.sweetiez.api.core.orders.models.responses.OrderStatusUpdatedResponse;
import fr.sweetiez.api.core.orders.models.responses.PaymentIntentResponse;
import fr.sweetiez.api.core.orders.ports.OrdersNotifier;
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.core.orders.services.exceptions.InvalidOrderException;
import fr.sweetiez.api.core.orders.services.exceptions.OrderNotFoundException;
import fr.sweetiez.api.core.orders.services.exceptions.PaymentIntentException;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.products.services.TrayService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class OrderService {
    private final OrdersWriter writer;
    private final OrdersReader reader;
    private final SweetService sweetService;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final OrdersNotifier notifier;
    private final TrayService trayService;

    private final LoyaltyPointService loyaltyPointService;

    public OrderService(OrdersWriter writer, OrdersReader reader, SweetService sweetService,
                        CustomerService customerService, PaymentService paymentService,
                        OrdersNotifier notifier, TrayService trayService,
                        LoyaltyPointService loyaltyPointService) {
        this.writer = writer;
        this.reader = reader;
        this.sweetService = sweetService;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.notifier = notifier;
        this.trayService = trayService;
        this.loyaltyPointService = loyaltyPointService;
    }

    public OrderCreatedResponse create(CreateOrderRequest request) throws InvalidOrderException {
        Optional<CustomerId> customerId;
        try {
            customerId = Optional.of(customerService.findByEmail(request.email()).id());
        } catch (Exception e) {
            customerId = Optional.empty();
        }
        // Retrieve the products by their ids
        var sweetProducts = getProducts(request);
        var totalPrice = computeTotalPrice(sweetProducts);
        // Create the order
        var order = new Order(request, sweetProducts, customerId,
                new BigDecimal(totalPrice).setScale(2, RoundingMode.UP).doubleValue(),
                LocalDate.now());

        // Check if the order is valid
        if (!order.isValid()) {
            throw new InvalidOrderException();
        }

        // Save the order
        return new OrderCreatedResponse(this.writer.save(order));
    }

    public List<DetailedOrderResponse> getAll() {
        var orders = this.reader.findAll();
        return orders.orders().stream()
                .map(DetailedOrderResponse::new)
                .toList();
    }

    public DetailedOrderResponse getById(String id) throws OrderNotFoundException {
        return this.reader.findById(id).stream()
                .map(DetailedOrderResponse::new)
                .findFirst().orElseThrow(OrderNotFoundException::new);
    }

    private List<ProductOrder> getProductsByType(Collection<ProductOrderRequest> requestedProducts, ProductType productType) {
        var productsOrdered = requestedProducts
                .stream()
                .filter(productRequest -> productRequest.type() == productType)
                .toList();

        var service = productType == ProductType.SWEET ? sweetService : trayService;
        var products = new ArrayList<ProductOrder>();

        service.retrieveAllProductsByIds(productsOrdered.stream().map(ProductOrderRequest::productId).toList())
                .forEach(sweet -> {
                    for (var item : productsOrdered) {
                        if (sweet.id().value().equals(item.productId())) {
                            products.add(new ProductOrder(
                                    item.productId().toString(),
                                    sweet.name().value(),
                                    productType,
                                    item.quantity(),
                                    sweet.price().packaged().doubleValue()
                            ));
                        }
                    }
                });

        return products;
    }

    private List<ProductOrder> getProducts(CreateOrderRequest request) {
        var products = getProductsByType(request.products(), ProductType.SWEET);
        products.addAll(getProductsByType(request.products(), ProductType.TRAY));

        return products;
    }

    public PaymentIntentResponse paymentIntent(String orderId) throws OrderNotFoundException, PaymentIntentException {
        var order = new Order(this.reader.findById(orderId).orElseThrow(OrderNotFoundException::new), Set.of());
        var paymentIntent = this.paymentService.createPaymentIntent(order);

        var updatedOrder = new Order(
                order.id(),
                order.customerInfo(),
                order.pickupDate(),
                order.status(),
                order.createdAt(),
                order.totalPrice(),
                order.products(),
                order.customerId(),
                removeClientSecretFromPaymentIntent(paymentIntent.clientSecret())
        );
        this.writer.save(updatedOrder);
        return  paymentIntent;
    }

    public OrderStatusUpdatedResponse confirmPayment(String paymentIntent) throws OrderNotFoundException {
        var order = new Order(this.reader.findByPaymentIntent(paymentIntent).orElseThrow(OrderNotFoundException::new), Set.of());

        var updatedOrder = new Order(
                order.id(),
                order.customerInfo(),
                order.pickupDate(),
                OrderStatus.PAID,
                order.createdAt(),
                order.totalPrice(),
                order.products(),
                order.customerId(),
                order.paymentIntent()
        );

        // Notify the customer
        notifier.notifyCustomer(order);

        // Compute the loyalty points
        if (updatedOrder.customerId().isPresent() && !updatedOrder.customerId().get().value().trim().equals("")) { //
            loyaltyPointService.addLoyaltyPoints(new AddLoyaltyPointsRequest(order));
        }

        return new OrderStatusUpdatedResponse(this.writer.save(updatedOrder));
    }

    public OrderStatusUpdatedResponse updateOrderStatus(String orderId, OrderStatus status) throws OrderNotFoundException {
        var order = new Order(this.reader.findById(orderId).orElseThrow(OrderNotFoundException::new), Set.of());

        Optional<CustomerId> customerId;
        try {
            customerId = Optional.of(customerService.findByEmail(order.customerInfo().email()).id());
        } catch (Exception e) {
            customerId = Optional.empty();
        }

        var updatedOrder = new Order(
                order.id(),
                order.customerInfo(),
                order.pickupDate(),
                status,
                order.createdAt(),
                order.totalPrice(),
                order.products(),
                customerId,
                order.paymentIntent()
        );
        return new OrderStatusUpdatedResponse(this.writer.save(updatedOrder));
    }

    public List<DetailedOrderResponse> retrieveClientOrders(String clientEmail) {
        var orders = this.reader.findByEmail(clientEmail);
        return orders.orders().stream()
                .map(DetailedOrderResponse::new)
                .toList();
    }

    private String removeClientSecretFromPaymentIntent(String paymentIntent){
        return paymentIntent.substring(0, 27);
    }


    /**
     * Computation of the total price of the order
     * @return unitPrice to pay
     */
    private double computeTotalPrice(List<ProductOrder> products) {
        return products.stream()
                .map(p -> p.unitPrice().unitPrice().doubleValue() * p.quantity().value())
                .reduce(0.0, Double::sum);
    }
}
