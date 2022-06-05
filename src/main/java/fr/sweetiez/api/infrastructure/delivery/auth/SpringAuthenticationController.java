package fr.sweetiez.api.infrastructure.delivery.auth;

import fr.sweetiez.api.adapter.delivery.authentication.AuthenticationEndPoints;
import fr.sweetiez.api.core.authentication.models.LoginRequest;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class SpringAuthenticationController {

    private final AuthenticationEndPoints authenticationEndPoints;

    public SpringAuthenticationController(AuthenticationEndPoints authenticationEndPoints) {
        this.authenticationEndPoints = authenticationEndPoints;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return authenticationEndPoints.login(request);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Object> subscribe(@RequestBody SubscriptionRequest request) {
        return authenticationEndPoints.subscribe(request);
    }

    @GetMapping("/refresh/token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        return authenticationEndPoints.refreshToken(request);
    }
}
