package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.roles.Role;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleEntity;

public class RoleMapper {

    public Role toDto(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    public RoleEntity toEntity(Role role) {
        return new RoleEntity(role.id(), role.name());
    }
}
