package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.RecipeMapper;
import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.requests.RemoveStepRequest;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepRepository;

import java.util.UUID;

public class RecipeWriterAdapter implements RecipeWriter {

    private final RecipeRepository repository;

    private final RecipeStepRepository stepRepository;

    private final RecipeMapper mapper;

    public RecipeWriterAdapter(RecipeRepository repository, RecipeStepRepository stepRepository, RecipeMapper mapper) {
        this.repository = repository;
        this.stepRepository = stepRepository;
        this.mapper = mapper;
    }

    @Override
    public Recipe save(Recipe recipe) {
        var recipeEntity = repository.save(mapper.toEntity(recipe));
        var list = recipe.steps().steps().stream()
                .map(step -> mapper.toEntity(step, recipeEntity.getId()))
                .map(stepRepository::save)
                .toList();
        return mapper.toDto(recipeEntity, list);
    }

    @Override
    public void removeStep(RemoveStepRequest request) {
        stepRepository.deleteById(UUID.fromString(request.stepId()));
    }
}
