package fr.sweetiez.api.customer.adapters;

import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.customer.domain.CustomerRepository;

import java.util.LinkedHashSet;
import java.util.Set;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final Set<Customer> customers= new LinkedHashSet<>();

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Set<Customer> all() {
        return customers;
    }
}
