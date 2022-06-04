package fr.sweetiez.api.core.customers.ports;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;

import java.util.Optional;
import java.util.UUID;

public interface CustomerReader {
    Optional<Customer> findById(CustomerId customerId);
    Optional<Customer> findByAccountId(UUID accountId);

    Optional<Customer> findByEmail(String email);
}
