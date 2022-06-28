package fr.sweetiez.api.infrastructure.repository.recipe;


import fr.sweetiez.api.core.recipes.models.recipes.details.RecipeDifficulty;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final String title;

    @Column(nullable = false, columnDefinition = "text")
    private final String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeDifficulty difficulty;

    @Column(nullable = false)
    private final Integer cost;

    @Column(nullable = false)
    private final Integer people;

    @Column()
    private final Integer preparationTime;

    @Column()
    private final Integer chillTime;

    @Column()
    private final Integer cookTime;

    @Column(columnDefinition = "text")
    private String images;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public RecipeEntity() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.difficulty = null;
        this.cost = null;
        this.people = null;
        this.preparationTime = null;
        this.chillTime = null;
        this.cookTime = null;
        this.images = null;
        this.state = null;
    }

    public RecipeEntity(UUID id, String title, String description,
                        RecipeDifficulty difficulty, Integer cost,
                        Integer people, Integer preparationTime,
                        Integer chillTime, Integer cookTime,
                        String images, State state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.cost = cost;
        this.people = people;
        this.preparationTime = preparationTime;
        this.chillTime = chillTime;
        this.cookTime = cookTime;
        this.images = images;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public RecipeDifficulty getDifficulty() {
        return difficulty;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getPeople() {
        return people;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public Integer getChillTime() {
        return chillTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public String getImages() {
        return images;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && difficulty == that.difficulty && Objects.equals(cost, that.cost) && Objects.equals(people, that.people) && Objects.equals(preparationTime, that.preparationTime) && Objects.equals(chillTime, that.chillTime) && Objects.equals(cookTime, that.cookTime) && Objects.equals(images, that.images) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, difficulty, cost, people, preparationTime, chillTime, cookTime, images, state);
    }

    @Override
    public String toString() {
        return "RecipeEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", cost=" + cost +
                ", people=" + people +
                ", preparationTime=" + preparationTime +
                ", chillTime=" + chillTime +
                ", cookTime=" + cookTime +
                ", images='" + images + '\'' +
                ", state=" + state +
                '}';
    }
}
