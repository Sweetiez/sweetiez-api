package fr.sweetiez.api.core.sweets.models.sweet;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SweetId(String value) {

    public boolean isValid() {
        try {
            UUID.fromString(value);
            return true;
        }
        catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public static SweetId generate(Sweets sweets) {
        Set<String> ids = sweets.content()
                .stream()
                .map(sweet -> sweet.id().value())
                .collect(Collectors.toSet());

        String id;

        do { id = UUID.randomUUID().toString(); } while (ids.contains(id));

        return new SweetId(id);
    }
}
