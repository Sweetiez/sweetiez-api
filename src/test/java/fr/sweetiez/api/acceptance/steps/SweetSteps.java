package fr.sweetiez.api.acceptance.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class SweetSteps implements En {

    public SweetSteps() {
        Given("^existing sweets:$", (DataTable dataTable) -> {
            throw new PendingException();
        });
    }
}
