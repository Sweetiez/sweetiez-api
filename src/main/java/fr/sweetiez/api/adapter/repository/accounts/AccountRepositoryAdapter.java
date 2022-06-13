package fr.sweetiez.api.adapter.repository.accounts;

import fr.sweetiez.api.adapter.shared.AccountMapper;
import fr.sweetiez.api.core.authentication.models.Account;
import fr.sweetiez.api.core.authentication.models.Role;
import fr.sweetiez.api.core.authentication.ports.AuthenticationRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;

import java.util.Optional;

public class AccountRepositoryAdapter implements AuthenticationRepository {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper mapper;

    public AccountRepositoryAdapter(AccountRepository accountRepository, RoleRepository roleRepository,
                                    AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public boolean accountExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    public Account registerAccount(Account account) {
        return mapper.toDto(accountRepository.save(mapper.toEntity(account)));
    }

    public Role getUserRole() {
        return mapper.toDto(roleRepository.findByName("USER"));
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username)
                .map(mapper::toDto);
    }
}
