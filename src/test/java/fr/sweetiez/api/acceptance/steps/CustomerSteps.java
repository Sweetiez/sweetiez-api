package fr.sweetiez.api.acceptance.steps;

import fr.sweetiez.api.customer.domain.Customer;
import fr.sweetiez.api.customer.domain.CustomerRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;


public class CustomerSteps implements En {

    public CustomerSteps(CustomerRepository customerRepository) {

        Given("^existing customers:$", (DataTable dataTable) -> {
            var dataMaps = dataTable.asMaps();
            dataMaps.forEach(dataMap -> {
                var customer = new Customer(dataMap.get("id"), dataMap.get("firstName"),
                        dataMap.get("lastName"), dataMap.get("email"));
                customerRepository.add(customer);
            });
        });
    }

}
