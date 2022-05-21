package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.AuthenticationEndPoints;
import fr.sweetiez.api.core.authentication.models.LoginRequest;
import fr.sweetiez.api.core.authentication.models.SubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
