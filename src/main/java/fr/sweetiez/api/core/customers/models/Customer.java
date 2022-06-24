package fr.sweetiez.api.core.customers.models;

import fr.sweetiez.api.core.authentication.models.Account;

import java.util.Optional;

public record Customer(
        CustomerId id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Optional<Account> account,
        Integer loyaltyPoints
) {
    public Customer(Customer customer, UpdateCustomerRequest request) {
        this(
                customer.id,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone(),
                customer.account,
                customer.loyaltyPoints
        );
    }
}
