package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.roles.Role;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountEntity;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleEntity;

public class AccountMapper {

    public RoleEntity toEntity(Role role) {
        return new RoleEntity(role.id(), role.name());
    }

    public Role toDto(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    public AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.id(),
                account.username(),
                account.password(),
                account.roles()
                        .stream()
                        .map(this::toEntity)
                        .toList(),
                account.updatePasswordToken()
        );
    }

    public Account toDto(AccountEntity entity) {
        return new Account(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRoles()
                        .stream()
                        .map(this::toDto)
                        .toList(),
                entity.getPasswordUpdateToken()
        );
    }
}
