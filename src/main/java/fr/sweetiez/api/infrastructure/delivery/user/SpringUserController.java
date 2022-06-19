package fr.sweetiez.api.infrastructure.delivery.user;

import fr.sweetiez.api.adapter.delivery.user.UserEndPoints;
import fr.sweetiez.api.core.customers.models.responses.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequestMapping("/user")
public class SpringUserController {

    private final UserEndPoints userEndPoints;

    public SpringUserController(UserEndPoints userEndPoints) {
        this.userEndPoints = userEndPoints;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        return userEndPoints.getProfile(authentication.getName());
    }
}
