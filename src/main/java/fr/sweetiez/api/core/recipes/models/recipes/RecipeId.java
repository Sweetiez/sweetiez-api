package fr.sweetiez.api.core.recipes.models.recipes;

import java.util.UUID;

public record RecipeId(UUID id) {

    public RecipeId() {
        this(UUID.randomUUID());
    }

    public RecipeId(String value) {
        this(UUID.fromString(value));
    }

    public RecipeId(UUID id) {
        this.id = id;
    }
}
