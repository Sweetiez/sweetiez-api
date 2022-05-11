package fr.sweetiez.api.core.employees.models;

import java.util.UUID;

public record EmployeeId(String value) {

    public boolean isValid() {
        try {
            UUID.fromString(value);
            return true;
        }
        catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
