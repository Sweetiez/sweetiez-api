package fr.sweetiez.api.authentication;

import fr.sweetiez.api.customer.domain.Customer;

import java.util.Optional;

public class InMemoryAuthenticationService implements AuthenticationService {

    private Customer currentCustomer;

    public void authenticate(Customer customer) {
        currentCustomer = customer;
    }

    public Optional<Customer> currentCustomer() {
        return Optional.ofNullable(currentCustomer);
    }
}
