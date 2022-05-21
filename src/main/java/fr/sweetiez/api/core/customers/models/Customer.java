package fr.sweetiez.api.core.customers.models;

import fr.sweetiez.api.core.authentication.models.Account;

import java.util.Optional;

public record Customer(
        CustomerId id,
        String firstName,
        String lastName,
        String email,
        Optional<Account> account
) {}
