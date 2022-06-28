package fr.sweetiez.api.core.customers.models.responses;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;

public record UserProfileResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer loyaltyPoints
) {

    public  UserProfileResponse(Customer customer) {
        this(customer.id().value(), customer.firstName(), customer.lastName(), customer.email(), customer.phone(), customer.loyaltyPoints());
    }

}
