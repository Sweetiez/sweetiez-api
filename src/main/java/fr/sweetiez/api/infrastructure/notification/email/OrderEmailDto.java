package fr.sweetiez.api.infrastructure.notification.email;

import javax.validation.constraints.Email;

public record OrderEmailDto(
        @Email
        String to,
        String subject,
        String firstName,
        String lastName,
        String commandId,
        double total
) {


}
