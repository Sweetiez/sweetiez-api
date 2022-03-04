package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class LoyaltySteps implements En {

    public LoyaltySteps() {
        And("^my points of loyalty are \"([^\"]*)\"$", (Long amount) -> {
            throw new PendingException();
        });

        And("^my loyalty points are \"([^\"]*)\"$", (Long amount) -> {
            throw new PendingException();
        });
    }
}
