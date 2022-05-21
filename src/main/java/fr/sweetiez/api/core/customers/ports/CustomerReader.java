package fr.sweetiez.api.core.customers.ports;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;

import java.util.Optional;

public interface CustomerReader {
    Optional<Customer> findById(CustomerId customerId);
}
