package fr.sweetiez.api.core.customers.models;

public record UpdateCustomerRequest(
        String id,
        String firstName,
        String lastName,
        String email,
        String phone
) {}
