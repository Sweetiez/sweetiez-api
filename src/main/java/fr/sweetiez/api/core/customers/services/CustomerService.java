package fr.sweetiez.api.core.customers.services;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;

import java.util.UUID;

public class CustomerService {

    private final CustomerReader reader;
    private final CustomerWriter writer;

    public CustomerService(CustomerReader reader, CustomerWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public boolean exists(CustomerId customerId) {
        return reader.findById(customerId).isPresent();
    }

    public Customer save(Customer customer) {
        return writer.save(customer);
    }

    public Customer findByAccountId(UUID accountId) {
        return reader.findByAccountId(accountId).orElseThrow();
    }
    public Customer findByEmail(String email) {
        return reader.findByEmail(email).orElseThrow();
    }
}
