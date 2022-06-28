package fr.sweetiez.api.core.authentication.models;

public record SubscriptionRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone
) {}
