package fr.sweetiez.api.infrastructure.delivery.roles;

import fr.sweetiez.api.adapter.delivery.role.RoleEndPoints;
import fr.sweetiez.api.core.roles.models.AccountResponse;
import fr.sweetiez.api.core.roles.models.Role;
import fr.sweetiez.api.core.roles.models.RoleName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/admin/roles")
public class SpringRoleController {

    private final RoleEndPoints roleEndPoints;

    public SpringRoleController(RoleEndPoints roleEndPoints) {
        this.roleEndPoints = roleEndPoints;
    }

    @GetMapping
    public ResponseEntity<Collection<Role>> retrieveAll() {
        return roleEndPoints.retrieveAll();
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody RoleName roleName) {
        return roleEndPoints.create(roleName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody RoleName roleName) {
        return roleEndPoints.update(id, roleName.name());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<AccountResponse> addRoleToAccount(@PathVariable UUID id, @RequestBody Roles request) {
        return roleEndPoints.updateUserRoles(id, request.roles());
    }

}
