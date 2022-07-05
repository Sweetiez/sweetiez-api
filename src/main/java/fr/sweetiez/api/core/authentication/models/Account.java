package fr.sweetiez.api.core.authentication.models;

import fr.sweetiez.api.core.roles.models.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record Account(UUID id, String username, String password, Collection<Role> roles, String updatePasswordToken) {
    public Account updateRoles(List<Role> newRoles) {
        return new Account(id, username, password, newRoles, updatePasswordToken);
    }
}
