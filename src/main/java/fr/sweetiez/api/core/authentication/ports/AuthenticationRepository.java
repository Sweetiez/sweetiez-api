package fr.sweetiez.api.core.authentication.ports;

import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.roles.Role;

import java.util.Optional;

public interface AuthenticationRepository {
    boolean accountExists(String username);
    Account registerAccount(Account account);
    Role getUserRole();
    Optional<Account> findByUsername(String username);
    Optional<Account> findByResetPasswordToken(String token);
}
