package fr.sweetiez.api.account;

import java.util.Optional;
import java.util.Set;

public interface CustomerAccountRepository {
    void add(CustomerAccount account);
    Optional<CustomerAccount> findById(String id);
    Set<CustomerAccount> all();
    void update(CustomerAccount customerAccount);
}
