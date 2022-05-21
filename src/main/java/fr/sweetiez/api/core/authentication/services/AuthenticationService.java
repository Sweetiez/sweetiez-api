package fr.sweetiez.api.core.authentication.services;

import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import fr.sweetiez.api.core.authentication.ports.AuthenticationRepository;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.services.CustomerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final CustomerService customerService;

    public AuthenticationService(AuthenticationRepository repository, CustomerService customerService) {
        this.repository = repository;
        this.customerService = customerService;
    }

    public Customer register(SubscriptionRequest request) {
        var accountExists = repository.accountExists(request.email());

        if (accountExists) {
            throw new AccountAlreadyExistsException();
        }

        var password = new BCryptPasswordEncoder().encode(request.password());
        var account = new Account(null, request.email(), password, List.of(repository.getUserRole()));
        var createdAccount = repository.registerAccount(account);
        var customer = new Customer(
                null,
                request.firstName(),
                request.lastName(),
                request.email(),
                Optional.of(createdAccount));

        return customerService.save(customer);
    }
}
