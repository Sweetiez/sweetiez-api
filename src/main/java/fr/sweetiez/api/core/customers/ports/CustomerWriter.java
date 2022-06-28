package fr.sweetiez.api.core.customers.ports;

import fr.sweetiez.api.core.customers.models.Customer;

public interface CustomerWriter {
    Customer save(Customer customer);
}
