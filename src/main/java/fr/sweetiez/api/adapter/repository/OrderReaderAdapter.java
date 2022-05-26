package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.OrderMapper;
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;

public class OrderReaderAdapter implements OrdersReader {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    public OrderReaderAdapter(final OrderRepository repository, final OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
