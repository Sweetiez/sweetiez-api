package fr.sweetiez.api.adapter.repository.roles;

import fr.sweetiez.api.adapter.shared.RoleMapper;
import fr.sweetiez.api.core.roles.Role;
import fr.sweetiez.api.core.roles.Roles;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;

import java.util.Collection;

public class RolesAdapter implements Roles {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RolesAdapter(RoleRepository roleRepository, RoleMapper roleMapper) {
        repository = roleRepository;
        mapper = roleMapper;
    }

    public Collection<Role> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public Role save(Role role) {
        return mapper.toDto(repository.save(mapper.toEntity(role)));
    }
}
