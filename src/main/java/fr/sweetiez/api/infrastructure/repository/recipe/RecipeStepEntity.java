package fr.sweetiez.api.infrastructure.repository.recipe;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recipe_step")
public class RecipeStepEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final UUID recipeId;

    @Column()
    private final Integer stepOrder;

    @Column(nullable = false, columnDefinition = "text")
    private final String description;

    public RecipeStepEntity() {
        this.id = null;
        this.recipeId = null;
        this.stepOrder = null;
        this.description = null;
    }

    public RecipeStepEntity(UUID id, UUID recipeId, Integer order, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.stepOrder = order;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public Integer getOrder() {
        return stepOrder;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeStepEntity that = (RecipeStepEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(recipeId, that.recipeId) && Objects.equals(stepOrder, that.stepOrder) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, stepOrder, description);
    }

    @Override
    public String toString() {
        return "RecipeStepEntity{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", order=" + stepOrder +
                ", description='" + description + '\'' +
                '}';
    }
}
