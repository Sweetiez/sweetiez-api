package fr.sweetiez.api.infrastructure.repository.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);
    Optional<AccountEntity> findByPasswordUpdateToken(String token);

    Collection<AccountEntity> findByRoles_Name(String name);
}
