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

    public Role update(Long id, String newName) {
        if (ReservedRole.isReserved(newName)) {
            throw new ReservedRoleException();
        }

        var roles = this.roles.findAll();

        var role = roles.stream()
                .filter(r -> r.id().equals(id))
                .findFirst()
                .orElseThrow();

        if (roles.stream().anyMatch(r -> r.name().equals(newName))) {
            throw new RoleAlreadyExistsException();
        }

        return this.roles.save(new Role(role.id(), newName));
    }
}
