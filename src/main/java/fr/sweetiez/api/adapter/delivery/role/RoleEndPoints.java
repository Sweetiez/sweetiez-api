package fr.sweetiez.api.adapter.delivery.role;

import fr.sweetiez.api.core.roles.CreateRoleRequest;
import fr.sweetiez.api.core.roles.Role;
import fr.sweetiez.api.core.roles.RoleService;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;

public class RoleEndPoints {

    private final RoleService roleService;

    public RoleEndPoints(RoleService roleService) {
        this.roleService = roleService;
    }

    public ResponseEntity<Collection<Role>> retrieveAll() {
        return ResponseEntity.ok(roleService.retrieveAll());
    }

    public ResponseEntity<?> create(CreateRoleRequest request) {
        try {
            Role role = roleService.create(request.name());
            return ResponseEntity.created(URI.create("/roles/" + role.id())).build();
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
