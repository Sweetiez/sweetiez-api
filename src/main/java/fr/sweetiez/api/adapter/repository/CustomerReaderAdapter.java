package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.CustomerMapper;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

public class CustomerReaderAdapter implements CustomerReader {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerReaderAdapter(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<Customer> findById(CustomerId customerId) {
        return repository.findById(UUID.fromString(customerId.value()))
                .map(mapper::toDto);
    }
}