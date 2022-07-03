package fr.sweetiez.api.infrastructure.delivery.roles;

import fr.sweetiez.api.adapter.delivery.role.RoleEndPoints;
import fr.sweetiez.api.core.roles.models.RoleName;
import fr.sweetiez.api.core.roles.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class SpringRoleController {

    private final RoleEndPoints roleEndPoints;

    public SpringRoleController(RoleEndPoints roleEndPoints) {
        this.roleEndPoints = roleEndPoints;
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> retrieveAll() {
        return roleEndPoints.retrieveAll();
    }

    @PostMapping("/admin/roles")
    private ResponseEntity<?> create(@RequestBody RoleName roleName) {
        return roleEndPoints.create(roleName);
    }

    @PutMapping("/admin/roles/{id}")
    private ResponseEntity<Role> update(@PathVariable Long id, @RequestBody RoleName roleName) {
        return roleEndPoints.update(id, roleName.name());
    }
}
