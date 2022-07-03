package fr.sweetiez.api.infrastructure.delivery.roles;

import fr.sweetiez.api.adapter.delivery.role.RoleEndPoints;
import fr.sweetiez.api.core.roles.CreateRoleRequest;
import fr.sweetiez.api.core.roles.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private ResponseEntity<?> create(@RequestBody CreateRoleRequest request) {
        return roleEndPoints.create(request);
    }
}
