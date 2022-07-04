package fr.sweetiez.api.core.roles.models;

import java.util.Collection;

public record AccountResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Collection<Role> roles
) {}
