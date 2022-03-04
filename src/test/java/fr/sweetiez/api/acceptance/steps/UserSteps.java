package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class UserSteps implements En {

    public UserSteps() {
        Given("^I am authenticated as \"([^\"]*)\"$", (String email) -> {
            throw new PendingException();
        });
    }
}
