package fr.sweetiez.api.core.customers.models.responses;
import fr.sweetiez.api.core.customers.models.Customer;

public record UpdateCustomerProfileResponse(
        String id,
        String lastName,
        String firstName,
        String email,
        String phone
)
{
    public UpdateCustomerProfileResponse(Customer customer) {
        this(
                customer.id().value(),
                customer.lastName(),
                customer.firstName(),
                customer.email(),
                customer.phone()
        );
    }
}
