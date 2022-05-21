package fr.sweetiez.api.core.authentication.ports;

import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.authentication.models.Role;

public interface AuthenticationRepository {
    boolean accountExists(String username);
    Account registerAccount(Account account);
    Role getUserRole();
}
