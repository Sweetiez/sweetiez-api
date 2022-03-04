package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class PayPalSteps implements En {
    public PayPalSteps() {
        And("^my PayPal details are correct$", () -> {
            throw new PendingException();
        });
        And("^my PayPal details are incorrect$", () -> {
            throw new PendingException();
        });
    }
}
