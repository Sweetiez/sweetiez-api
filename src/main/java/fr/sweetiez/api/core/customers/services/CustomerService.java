package fr.sweetiez.api.core.customers.services;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.models.UpdateCustomerRequest;
import fr.sweetiez.api.core.customers.models.responses.UpdateCustomerProfileResponse;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;
import fr.sweetiez.api.core.roles.models.AccountResponse;

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

    public Customer findById(String customerId) {
        return reader.findById(new CustomerId(customerId)).orElseThrow();
    }

    public UpdateCustomerProfileResponse updateCustomerDetails(UpdateCustomerRequest request) {
        var customer = reader.findById(new CustomerId(request.id())).orElseThrow();
        var updatedCustomer = new Customer(customer, request);
        var updated = writer.save(updatedCustomer);

        return new UpdateCustomerProfileResponse(updated);
    }

    public AccountResponse accountDetails(String email) {
        var customer = reader.findByEmail(email).orElseThrow();
        var account = customer.account().orElseThrow();

        return new AccountResponse(
                customer.id().value(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                account.roles());
    }
}
