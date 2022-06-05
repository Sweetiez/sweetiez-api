package fr.sweetiez.api.core.authentication.models;

public record UpdateAccountPasswordRequest(
        String email,
        String currentPassword,
        String newPassword
) {}
