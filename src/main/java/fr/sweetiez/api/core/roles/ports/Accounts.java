package fr.sweetiez.api.core.roles.ports;

import fr.sweetiez.api.core.authentication.models.Account;

public interface Accounts {
    Account save(Account account);
}
