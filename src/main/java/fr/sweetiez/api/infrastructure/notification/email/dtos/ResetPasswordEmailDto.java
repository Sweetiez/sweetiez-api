package fr.sweetiez.api.infrastructure.notification.email.dtos;

public record ResetPasswordEmailDto(String email, String resetPasswordToken, String resetLink) {

    public ResetPasswordEmailDto(String email, String resetPasswordToken) {
        this(email, resetPasswordToken, "");
    }

    public ResetPasswordEmailDto(ResetPasswordEmailDto dto, String resetLink) {
        this(dto.email, dto.resetPasswordToken, resetLink);
    }
}
