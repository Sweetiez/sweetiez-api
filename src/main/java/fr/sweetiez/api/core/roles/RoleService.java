package fr.sweetiez.api.core.roles;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class RoleService {

    private final Roles roles;

    public RoleService(Roles roles) {
        this.roles = roles;
    }

    public ResponseEntity<Collection<Role>> retrieveAll() {
        return ResponseEntity.ok(roles.findAll());
    }
}
