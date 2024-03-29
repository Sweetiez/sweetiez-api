package fr.sweetiez.api.adapter.repository.roles;

import fr.sweetiez.api.adapter.shared.RoleMapper;
import fr.sweetiez.api.core.roles.models.Role;
import fr.sweetiez.api.core.roles.ports.Roles;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;

import java.util.Collection;
import java.util.Optional;

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

    public Optional<Role> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }
}
