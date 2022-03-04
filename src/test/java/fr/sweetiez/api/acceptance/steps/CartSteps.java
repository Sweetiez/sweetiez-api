package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class CartSteps implements En {

    public CartSteps() {
        And("^the cart value is \"([^\"]*)\" with \"([^\"]*)\" items remaining$", (String arg0, String arg1) -> {
            throw new PendingException();
        });
    }
}
