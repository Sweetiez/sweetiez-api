package fr.sweetiez.api.infrastructure.repository.accounts;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(unique = true)
    private final String name;

    public RoleEntity() {
        id = null;
        name = null;
    }

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
