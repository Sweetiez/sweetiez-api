package fr.sweetiez.api.adapter.repository.orders;

import fr.sweetiez.api.adapter.shared.OrderMapper;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import fr.sweetiez.api.core.orders.models.orders.Orders;
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderReaderAdapter implements OrdersReader {

    private final OrderRepository repository;

    private final OrderDetailRepository detailRepository;

    private final OrderMapper mapper;

    public OrderReaderAdapter(final OrderRepository repository,
                              final OrderDetailRepository detailRepository,
                              final OrderMapper mapper) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.mapper = mapper;
    }

    @Override
    public Orders findAll() {
        var orderDetails = this.detailRepository.findAll();
        return new Orders(this.repository.findAll().stream()
                .map(entity -> this.mapper.toDto(
                        entity, orderDetails.stream()
                                            .filter(detail -> detail.getOrderId().equals(entity.getId()))
                                            .toList()))
                .collect(Collectors.toList()));

    }

    @Override
    public Optional<Order> findById(String id) {
        var orderDetails = this.detailRepository.findAllByOrderId(UUID.fromString(id));
        return this.repository.findById(UUID.fromString(id))
                .stream().map(entity -> this.mapper.toDto(entity, orderDetails))
                .findFirst();
    }

    @Override
    public Optional<Order> findByPaymentIntent(String paymentIntent) {
        var entity = this.repository.findByPaymentIntent(paymentIntent).orElseThrow();
        var orderDetails = this.detailRepository.findAllByOrderId(UUID.fromString(entity.getId().toString()));
        return Optional.of(this.mapper.toDto(entity, orderDetails));
    }

    @Override
    public Orders findByEmail(String clientEmail) {
        var entityOrders = this.repository.findByEmail(clientEmail);
        return new Orders(entityOrders.stream()
                .map(entity -> this.mapper.toDto(entity, this.detailRepository.findAllByOrderId(entity.getId())))
                .toList());
    }
    @Override
    public Orders findByEmailIfPaid(String clientEmail) {
        var entityOrders = this.repository.findByEmailAndStatusEquals(clientEmail, OrderStatus.PAID);
        return new Orders(entityOrders.stream()
                .map(entity -> this.mapper.toDto(entity, this.detailRepository.findAllByOrderId(entity.getId())))
                .toList());
    }
}
