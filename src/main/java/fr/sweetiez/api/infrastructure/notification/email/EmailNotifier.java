package fr.sweetiez.api.infrastructure.notification.email;

import fr.sweetiez.api.infrastructure.notification.email.dtos.OrderEmailDto;
import fr.sweetiez.api.infrastructure.notification.email.dtos.ResetPasswordEmailDto;

public interface EmailNotifier {

    void send(OrderEmailDto emailDto);

    void send(ResetPasswordEmailDto emailDto);
}
