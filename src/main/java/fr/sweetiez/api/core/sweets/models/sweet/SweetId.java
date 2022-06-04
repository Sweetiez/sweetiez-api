package fr.sweetiez.api.core.sweets.models.sweet;

import java.util.UUID;
import java.util.stream.Collectors;

public record SweetId(String value) {

    public boolean isValid() {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
