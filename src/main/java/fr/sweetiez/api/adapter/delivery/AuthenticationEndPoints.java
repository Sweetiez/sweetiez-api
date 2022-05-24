package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.authentication.models.LoginRequest;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import fr.sweetiez.api.core.authentication.services.AccountAlreadyExistsException;
import fr.sweetiez.api.core.authentication.services.AuthenticationService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationEndPoints {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationEndPoints(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager, AuthenticationService authenticationService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<Object> login(LoginRequest request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());

            Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);

            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AUTHORIZATION, "Bearer " + accessToken);
            httpHeaders.add("refresh-token", "Bearer " + refreshToken);

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
            var refreshToken = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
            var claims = tokenProvider.parseToken(refreshToken);
            var username = claims.getBody().getSubject();
            var account = authenticationService.findByUsername(username);

            String accessToken = tokenProvider.createAccessToken(account);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AUTHORIZATION, "Bearer " + accessToken);
            httpHeaders.add("refresh-token", "Bearer " + refreshToken);

            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
