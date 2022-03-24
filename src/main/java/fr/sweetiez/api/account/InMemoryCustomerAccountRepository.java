package fr.sweetiez.api.account;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryCustomerAccountRepository implements CustomerAccountRepository {

    private final Set<CustomerAccount> accounts = new LinkedHashSet<>();

    public void add(CustomerAccount account) {
        accounts.add(account);
    }

    public Optional<CustomerAccount> findById(String id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }

    public Set<CustomerAccount> all() {
        return accounts;
    }

    public void update(CustomerAccount customerAccount) {
        accounts.stream()
                .filter(account -> account.getId().equals(customerAccount.getId()))
                .findFirst()
                .ifPresent(account -> account = customerAccount);

    }
}
