package fr.sweetiez.api.acceptance.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class CreditCardSteps implements En {

    public CreditCardSteps() {
        And("^my credit card details are correct$", (DataTable dataTable) -> {
            throw new PendingException();
        });
    }
}
