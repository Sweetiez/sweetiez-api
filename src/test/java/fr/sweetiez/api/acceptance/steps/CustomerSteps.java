package fr.sweetiez.api.acceptance.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;


public class CustomerSteps implements En {

    public CustomerSteps() {
        Given("^existing customers:$", (DataTable dataTable) -> {
            throw new PendingException();
        });
    }

}
