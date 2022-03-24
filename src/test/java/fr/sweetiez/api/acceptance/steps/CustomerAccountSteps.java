package fr.sweetiez.api.acceptance.steps;

import fr.sweetiez.api.account.CustomerAccount;
import fr.sweetiez.api.account.CustomerAccountRepository;
import fr.sweetiez.api.authentication.AuthenticationService;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerAccountSteps implements En {
    public CustomerAccountSteps(CustomerAccountRepository customerAccountRepository,
                                AuthenticationService authenticationService)
    {
        And("^my loyalty points are \"([^\"]*)\"$", (Long points) -> {
            authenticationService.currentCustomer().ifPresent(customer -> {
                var expectedCustomerAccount = new CustomerAccount(customer.getId(), points);

                if (shouldInitCustomerAccount(customerAccountRepository)) {
                    customerAccountRepository.add(expectedCustomerAccount);
                }
                else {
                    customerAccountRepository
                            .findById(customer.getId())
                            .ifPresent(account -> assertEquals(expectedCustomerAccount, account));
                }
            });
        });
    }

    private boolean shouldInitCustomerAccount(CustomerAccountRepository customerAccountRepository) {
        return customerAccountRepository.all().isEmpty();
    }
}
