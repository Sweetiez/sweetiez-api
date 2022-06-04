package fr.sweetiez.api.infrastructure.repository.ingredients;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Collection<HealthPropertyEntity> healthProperties;

    public IngredientEntity() {
        id = null;
        name = null;
        healthProperties = null;
    }

    public IngredientEntity(UUID id, String name, Collection<HealthPropertyEntity> healthProperties) {
        this.id = id;
        this.name = name;
        this.healthProperties = healthProperties;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<HealthPropertyEntity> getHealthProperties() {
        return healthProperties;
    }
}
