package fr.sweetiez.api.adapter.delivery.role;

import fr.sweetiez.api.core.roles.RoleName;
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

    public ResponseEntity<?> create(RoleName request) {
        try {
            Role role = roleService.create(request.name());
            return ResponseEntity.created(URI.create("/roles/" + role.id())).build();
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Role> update(Long id, String name) {
        try {
            return ResponseEntity.ok(roleService.update(id , name));
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
