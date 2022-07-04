package fr.sweetiez.api.core.roles.services;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.roles.models.AccountResponse;
import fr.sweetiez.api.core.roles.exceptions.InvalidRoleProvidedException;
import fr.sweetiez.api.core.roles.exceptions.ReservedRoleException;
import fr.sweetiez.api.core.roles.exceptions.RoleAlreadyExistsException;
import fr.sweetiez.api.core.roles.models.Role;
import fr.sweetiez.api.core.roles.ports.Accounts;
import fr.sweetiez.api.core.roles.ports.Roles;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RoleService {

    private final Roles roles;
    private final Accounts accounts;
    private final CustomerReader customerReader;

    public RoleService(Roles roles, Accounts accounts, CustomerReader customerReader) {
        this.customerReader = customerReader;
        this.roles = roles;
        this.accounts = accounts;
    }

    public Collection<Role> retrieveAll() {
        return roles.findAll();
    }

    public Role create(String name) {
        var existingRoles = retrieveAll();

        var role = existingRoles.stream()
                .map(Role::name)
                .filter(value -> value.equals(name))
                .findFirst();

        if (role.isPresent()) {
            throw new RoleAlreadyExistsException();
        }

        return roles.save(new Role(null, name));
    }

    public Role update(Long id, String newName) {
        if (ReservedRole.isReserved(newName)) {
            throw new ReservedRoleException();
        }

        var roles = this.roles.findAll();

        var role = roles.stream()
                .filter(r -> r.id().equals(id))
                .findFirst()
                .orElseThrow();

        if (roles.stream().anyMatch(r -> r.name().equals(newName))) {
            throw new RoleAlreadyExistsException();
        }

        return this.roles.save(new Role(role.id(), newName));
    }

    public AccountResponse updateUserRoles(UUID id, List<Role> newRoles) {
        var roles = retrieveAll();

        if (!roles.containsAll(newRoles)) {
            throw new InvalidRoleProvidedException();
        }

        var customer = this.customerReader
                .findById(new CustomerId(id.toString()))
                .orElseThrow();

        var account = accounts.save(
                customer.account()
                        .orElseThrow()
                        .updateRoles(newRoles));

        return new AccountResponse(
                customer.id().value(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                account.roles());
    }
}
