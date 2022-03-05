package fr.sweetiez.api.acceptance.steps;

import fr.sweetiez.api.sweet.Sweet;
import fr.sweetiez.api.sweet.SweetRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;

public class SweetSteps implements En {

    public SweetSteps(SweetRepository sweetRepository) {
        Given("^existing sweets:$", (DataTable dataTable) -> {
            var dataMaps = dataTable.asMaps();
            dataMaps.forEach(dataMap -> {
                var sweet = new Sweet(dataMap.get("id"), dataMap.get("name"),
                        dataMap.get("category"), Double.valueOf(dataMap.get("price")));
                sweetRepository.add(sweet);
            });
        });
    }
}
