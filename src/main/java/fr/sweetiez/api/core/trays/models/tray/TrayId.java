package fr.sweetiez.api.core.trays.models.tray;

import java.util.UUID;

public record TrayId(String value) {

    public boolean isValid() {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
