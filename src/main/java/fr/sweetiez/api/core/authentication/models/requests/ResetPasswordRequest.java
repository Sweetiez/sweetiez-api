package fr.sweetiez.api.core.authentication.models.requests;

public record ResetPasswordRequest(
        String token,
        String newPassword,
        String confirmPassword
) {}
