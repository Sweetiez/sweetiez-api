package fr.sweetiez.api.adapter.delivery.user;

import fr.sweetiez.api.core.customers.models.UpdateCustomerRequest;
import fr.sweetiez.api.core.customers.models.responses.UserProfileResponse;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.roles.models.AccountResponse;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

public class UserEndPoints {

    private final CustomerService customerService;


    public UserEndPoints(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseEntity<UserProfileResponse> getProfile(String email) {
        var customer = customerService.findByEmail(email);
        return ResponseEntity.ok(new UserProfileResponse(customer));
    }

    public ResponseEntity<Object> updateProfile(UpdateCustomerRequest request) {
        try {
            return ResponseEntity.ok(customerService.updateCustomerDetails(request));
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<AccountResponse> getAccountDetails(String email) {
        try {
            return ResponseEntity.ok(customerService.accountDetails(email));
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
