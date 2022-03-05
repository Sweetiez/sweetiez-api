package fr.sweetiez.api.customer.domain;

import java.util.Set;

public interface CustomerRepository {
    void add(Customer customer);

    Set<Customer> all();
}
