package fr.sweetiez.api.core.authentication.models.requests;

public record ChangePasswordRequest(
        String email,
        String currentPassword,
        String newPassword
) {}
