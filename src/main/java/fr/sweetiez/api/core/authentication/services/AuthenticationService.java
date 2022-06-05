package fr.sweetiez.api.core.authentication.services;

import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.authentication.models.LoginRequest;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import fr.sweetiez.api.core.authentication.models.UpdateAccountPasswordRequest;
import fr.sweetiez.api.core.authentication.ports.AuthenticationRepository;
import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final CustomerService customerService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;

    public AuthenticationService(AuthenticationRepository repository, CustomerService customerService,
                                 TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager) {
        this.repository = repository;
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public HttpHeaders login(LoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);

        var account = repository.findByUsername(request.username())
                .orElseThrow(() -> new AuthenticationServiceException("Account not found"));

        var customer = customerService.findByAccountId(account.id());

        String accessToken = tokenProvider.createAccessToken(authentication, customer);
        String refreshToken = tokenProvider.createRefreshToken(authentication, customer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + accessToken);
        httpHeaders.add("refresh-token", "Bearer " + refreshToken);

        return httpHeaders;
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
                request.phone(),
                Optional.of(createdAccount));

        return customerService.save(customer);
    }

    public HttpHeaders refreshToken(HttpServletRequest request) {
        var refreshToken = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        var claims = tokenProvider.parseToken(refreshToken);
        var username = claims.getBody().getSubject();

        var account = repository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("Account not found"));

        var user = customerService.findByAccountId(account.id());
        String accessToken = tokenProvider.createAccessToken(account, user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + accessToken);
        httpHeaders.add("refresh-token", "Bearer " + refreshToken);

        return httpHeaders;
    }

    public void updatePassword(UpdateAccountPasswordRequest request) {
        var account = repository.findByUsername(request.email()).orElseThrow();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(request.currentPassword(), account.password())) {
            throw new AuthenticationServiceException("Password invalid");
        }

        var newPassword = new BCryptPasswordEncoder().encode(request.newPassword());
        var updatedAccount = new Account(account.id(), account.username(), newPassword, account.roles());
        repository.registerAccount(updatedAccount);
    }
}