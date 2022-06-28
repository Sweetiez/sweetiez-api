package fr.sweetiez.api.adapter.delivery.authentication;

import fr.sweetiez.api.core.authentication.models.LoginRequest;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import fr.sweetiez.api.core.authentication.models.requests.ChangePasswordRequest;
import fr.sweetiez.api.core.authentication.models.requests.UpdatePasswordRequest;
import fr.sweetiez.api.core.authentication.services.AccountAlreadyExistsException;
import fr.sweetiez.api.core.authentication.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.NoSuchElementException;

public class AuthenticationEndPoints {

    private final AuthenticationService authenticationService;

    public AuthenticationEndPoints(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<Object> login(LoginRequest request) {
        try {
            var httpHeaders = authenticationService.login(request);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        }
        catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> subscribe(SubscriptionRequest request) {
        try {
            var customer = authenticationService.register(request);
            return ResponseEntity.created(URI.create("/customers/" + customer.id().value())).build();
        }
        catch (AccountAlreadyExistsException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        try {
            var httpHeaders = authenticationService.refreshToken(request);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> updatePassword(ChangePasswordRequest request) {
        try {
            authenticationService.updatePassword(request);
            return ResponseEntity.ok().build();
        }
        catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> requestPasswordChange(UpdatePasswordRequest request) {
        try {
            authenticationService.updatePasswordRequest(request);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
