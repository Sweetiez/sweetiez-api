package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.core.authentication.ports.AccountNotifier;
import fr.sweetiez.api.infrastructure.notification.email.EmailNotifier;
import fr.sweetiez.api.infrastructure.notification.email.dtos.ResetPasswordEmailDto;

public class AccountNotifierAdapter implements AccountNotifier {

    private final EmailNotifier notifier;

    public AccountNotifierAdapter(EmailNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void sendResetPasswordLink(String email, String resetPasswordToken) {
        notifier.send(new ResetPasswordEmailDto(email, resetPasswordToken));
    }

    @Override
    public void notifyPasswordChange(String email) {

    }

    @Override
    public void notifyAccountCreation(String email) {

    }
}
