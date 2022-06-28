package fr.sweetiez.api.infrastructure.notification.email;

public interface EmailNotifier {

    void send(OrderEmailDto emailDto);
}
