package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.CustomerMapper;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;

public class CustomerWriterAdapter implements CustomerWriter {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public CustomerWriterAdapter(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Customer save(Customer customer) {
        return mapper.toDto(repository.save(mapper.toEntity(customer)));
    }
}
