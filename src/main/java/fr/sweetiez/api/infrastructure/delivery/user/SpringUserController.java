package fr.sweetiez.api.infrastructure.delivery.user;

import fr.sweetiez.api.adapter.delivery.user.UserEndPoints;
import fr.sweetiez.api.core.customers.models.UpdateCustomerRequest;
import fr.sweetiez.api.core.customers.models.responses.UserProfileResponse;
import fr.sweetiez.api.core.roles.models.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringUserController {

    private final UserEndPoints userEndPoints;

    public SpringUserController(UserEndPoints userEndPoints) {
        this.userEndPoints = userEndPoints;
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        return userEndPoints.getProfile(authentication.getName());
    }

    @PutMapping("/user/me")
    public ResponseEntity<Object> updateMyProfile(@RequestBody UpdateCustomerRequest request) {
        return userEndPoints.updateProfile(request);
    }

    @PostMapping("/admin/users/account")
    public ResponseEntity<AccountResponse> accountDetails(@RequestBody AccountEmail request) {
        return userEndPoints.getAccountDetails(request.email());
    }
}
