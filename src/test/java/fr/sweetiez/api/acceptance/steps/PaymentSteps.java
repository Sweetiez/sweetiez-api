package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class PaymentSteps implements En {

    public PaymentSteps() {
        When("^I try to pay the cart value$", () -> {
            throw new PendingException();
        });

        Then("^the purchase is a success$", () -> {
            throw new PendingException();
        });
        Then("^the purchase is a failure$", () -> {
            throw new PendingException();
        });
    }
}
