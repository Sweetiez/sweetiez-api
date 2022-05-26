package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.OrderMapper;
import fr.sweetiez.api.core.orders.models.orders.Order;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;

public class OrderWriterAdapter implements OrdersWriter {

    private final OrderRepository repository;

    private final OrderDetailRepository detailRepository;

    private final OrderMapper mapper;

    public OrderWriterAdapter(final OrderRepository repository, OrderDetailRepository detailRepository, final OrderMapper mapper) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.mapper = mapper;
    }

    @Override
    public Order save(Order order) {
        var list = order.products().stream()
                .map(product -> mapper.toEntity(product, order))
                .map(detailRepository::save)
                .toList();
        return mapper.toDto(repository.save(mapper.toEntity(order)), list);
    }
}
