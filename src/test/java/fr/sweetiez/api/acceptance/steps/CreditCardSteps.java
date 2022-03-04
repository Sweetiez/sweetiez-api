package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class CreditCardSteps implements En {

    public CreditCardSteps() {
        And("^my credit card details are correct$", () -> {
            throw new PendingException();
        });
        And("^my credit card details are incorrect$", () -> {
            throw new PendingException();
        });
    }
}
