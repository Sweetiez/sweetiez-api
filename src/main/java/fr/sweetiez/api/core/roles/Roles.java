package fr.sweetiez.api.core.roles;

import java.util.Collection;

public interface Roles {
    Collection<Role> findAll();
    Role save(Role role);
}
