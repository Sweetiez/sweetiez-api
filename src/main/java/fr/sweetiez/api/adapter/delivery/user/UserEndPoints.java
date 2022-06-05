package fr.sweetiez.api.adapter.delivery.user;

import fr.sweetiez.api.core.customers.models.responses.UserProfileResponse;
import fr.sweetiez.api.core.customers.services.CustomerService;
import org.springframework.http.ResponseEntity;

public class UserEndPoints {

    private final CustomerService customerService;


    public UserEndPoints(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseEntity<UserProfileResponse> getProfile(String email) {
        var customer = customerService.findByEmail(email);
        return ResponseEntity.ok(new UserProfileResponse(customer));
    }
}
