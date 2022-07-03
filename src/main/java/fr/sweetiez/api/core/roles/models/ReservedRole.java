package fr.sweetiez.api.core.roles.models;

import java.util.ArrayList;
import java.util.List;

public class ReservedRole {
    private final static List<String> values = new ArrayList<>(
            List.of(
                    "ADMIN",
                    "USER"
            )
    );

    public static boolean isReserved(String role) {
        return values.stream().anyMatch(reserved -> reserved.equals(role.toUpperCase()));
    }
}
