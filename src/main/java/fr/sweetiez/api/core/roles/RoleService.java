package fr.sweetiez.api.core.roles;

import java.util.Collection;

public class RoleService {

    private final Roles roles;

    public RoleService(Roles roles) {
        this.roles = roles;
    }

    public Collection<Role> retrieveAll() {
        return roles.findAll();
    }

    public Role create(String name) {
        var existingRoles = retrieveAll();

        existingRoles.stream()
                .map(Role::name)
                .filter(value -> value.equals(name))
                .findFirst()
                .orElseThrow();

        return roles.save(new Role(null, name));
    }
}
