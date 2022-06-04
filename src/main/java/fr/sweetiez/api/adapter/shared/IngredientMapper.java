package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.infrastructure.repository.ingredients.HealthPropertyEntity;
import fr.sweetiez.api.infrastructure.repository.ingredients.IngredientEntity;

public class IngredientMapper {

    public IngredientEntity toEntity(Ingredient ingredient) {
        var healthProperties = ingredient.healthProperties()
                .stream()
                .map(this::toEntity)
                .toList();

        return new IngredientEntity(ingredient.id(), ingredient.name(), healthProperties);
    }

    public Ingredient toDto(IngredientEntity entity) {
        var healthProperties = entity.getHealthProperties()
                .stream()
                .map(this::toDto)
                .toList();

        return new Ingredient(entity.getId(), entity.getName(), healthProperties);
    }

    public HealthPropertyEntity toEntity(HealthProperty dto) {
        return new HealthPropertyEntity(dto.id(), dto.name(), dto.type());
    }

    public HealthProperty toDto(HealthPropertyEntity entity) {
        return new HealthProperty(entity.getId(), entity.getName(), entity.getType());
    }
}
