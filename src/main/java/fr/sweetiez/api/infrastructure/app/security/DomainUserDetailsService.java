package fr.sweetiez.api.infrastructure.app.security;

import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DomainUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public DomainUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var appUser = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username " + username + " not found"));

        return User.builder()
                .username(username)
                .password(appUser.getPassword())
                .roles(appUser.getRoles().stream().map(RoleEntity::getName).toArray(String[]::new))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
