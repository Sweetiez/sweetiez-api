package fr.sweetiez.api.adapter.delivery.role;

import fr.sweetiez.api.core.roles.Role;
import fr.sweetiez.api.core.roles.RoleService;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class RoleEndPoints {

    private final RoleService roleService;

    public RoleEndPoints(RoleService roleService) {
        this.roleService = roleService;
    }

    public ResponseEntity<Collection<Role>> retrieveAll() {
        return roleService.retrieveAll();
    }
}
