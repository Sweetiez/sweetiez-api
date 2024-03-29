package fr.sweetiez.api.adapter.repository.customers;

import fr.sweetiez.api.adapter.shared.CustomerMapper;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.LoyaltyPoints;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointReader;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

public class CustomerReaderAdapter implements CustomerReader, LoyaltyPointReader {
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

    public Optional<Customer> findByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId).map(mapper::toDto);
    }

    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDto);
    }

    @Override
    public LoyaltyPoints getLoyaltyPoints(String customerId) {
        return mapper.toLoyaltyPoints(repository.findById(UUID.fromString(customerId)).get());
    }
}
