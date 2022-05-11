package fr.sweetiez.api.core.sweets.ports;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.employees.models.EmployeeId;

public interface SweetsWriter {
    Sweet save(Sweet sweet, EmployeeId employee);
}
