package fr.sweetiez.api.core.customers.models;

import fr.sweetiez.api.core.authentication.models.Account;

import java.util.Optional;

public record Customer(
        CustomerId id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Optional<Account> account
) {
    public Customer(CustomerId id, Optional<Account> account, UpdateCustomerRequest request) {
        this(
                id,
                request.lastName(),
                request.firstName(),
                request.email(),
                request.phone(),
                account
        );
    }
}
