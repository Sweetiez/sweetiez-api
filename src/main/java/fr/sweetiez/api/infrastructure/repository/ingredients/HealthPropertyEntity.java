package fr.sweetiez.api.infrastructure.repository.ingredients;

import fr.sweetiez.api.core.ingredients.models.HealthPropertyType;

import javax.persistence.*;

@Entity
@Table(name = "health_property")
public class HealthPropertyEntity {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(nullable = false, unique = true)
    private final String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final HealthPropertyType type;


    public HealthPropertyEntity() {
        id = null;
        name = null;
        type = null;
    }

    public HealthPropertyEntity(Long id, String name, HealthPropertyType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HealthPropertyType getType() {
        return type;
    }
}
