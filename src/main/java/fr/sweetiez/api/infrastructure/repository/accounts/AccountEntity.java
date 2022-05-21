package fr.sweetiez.api.infrastructure.repository.accounts;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final String username;

    @Column(nullable = false)
    private final String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Collection<RoleEntity> roles;

    public AccountEntity() {
        id = null;
        username = null;
        password = null;
        roles = null;
    }

    public AccountEntity(UUID id, String username, String password, Collection<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }
}
