package fr.sweetiez.api.adapter.delivery.role;

import fr.sweetiez.api.core.roles.models.AccountResponse;
import fr.sweetiez.api.core.roles.models.Role;
import fr.sweetiez.api.core.roles.models.RoleName;
import fr.sweetiez.api.core.roles.services.RoleService;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    public ResponseEntity<AccountResponse> updateUserRoles(UUID id, List<Role> roles) {
        try {
            return ResponseEntity.ok(roleService.updateUserRoles(id, roles));
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
