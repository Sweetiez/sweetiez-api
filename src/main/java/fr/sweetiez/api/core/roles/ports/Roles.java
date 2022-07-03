package fr.sweetiez.api.core.roles.ports;

import fr.sweetiez.api.core.roles.models.Role;

import java.util.Collection;
import java.util.Optional;

public interface Roles {
    Collection<Role> findAll();
    Role save(Role role);
    Optional<Role> findById(Long id);
}
