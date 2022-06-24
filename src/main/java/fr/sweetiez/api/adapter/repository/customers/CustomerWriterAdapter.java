package fr.sweetiez.api.adapter.repository.customers;

import fr.sweetiez.api.adapter.shared.CustomerMapper;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;
import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.LoyaltyPoints;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointWriter;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;

import java.util.UUID;

public class CustomerWriterAdapter implements CustomerWriter, LoyaltyPointWriter {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public CustomerWriterAdapter(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Customer save(Customer customer) {
        return mapper.toDto(repository.save(mapper.toEntity(customer)));
    }

    @Override
    public LoyaltyPoints save(LoyaltyPoints loyaltyPoints) {
        var customer = repository.findById(UUID.fromString(loyaltyPoints.customerId())).get();
        return mapper.toLoyaltyPoints(repository.save(mapper.toCustomerEntity(customer, loyaltyPoints)));
    }
}
