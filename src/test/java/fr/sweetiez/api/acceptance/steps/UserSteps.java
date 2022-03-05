package fr.sweetiez.api.acceptance.steps;

import fr.sweetiez.api.authentication.AuthenticationService;
import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.customer.domain.CustomerRepository;
import io.cucumber.java8.En;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps implements En {

    public UserSteps(CustomerRepository customerRepository,
                     AuthenticationService authenticationService) {

        Given("^I am authenticated as \"([^\"]*)\"$", (String email) -> {
            Optional<Customer> optionalCustomer = customerRepository.all()
                    .stream()
                    .filter(customer -> customer.getEmail().equals(email))
                    .findFirst();
            optionalCustomer.ifPresent(authenticationService::authenticate);
            assertTrue(authenticationService.currentCustomer().isPresent());
        });
    }
}
