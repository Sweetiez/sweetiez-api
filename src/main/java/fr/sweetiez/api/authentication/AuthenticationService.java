package fr.sweetiez.api.authentication;

import fr.sweetiez.api.customer.domain.Customer;

import java.util.Optional;

public interface AuthenticationService {
    void authenticate(Customer customer);
    Optional<Customer> currentCustomer();
}
