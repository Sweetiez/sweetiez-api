package fr.sweetiez.api.core.customers.models.responses;

import fr.sweetiez.api.core.customers.models.Customer;

public record UserProfileResponse(
        String firstName,
        String lastName,
        String email
) {

    public  UserProfileResponse(Customer customer) {
        this(customer.firstName(), customer.lastName(), customer.email());
    }

}