package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.RecipeMapper;
import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.recipes.Recipes;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepRepository;

import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeReaderAdapter implements RecipeReader {

    private final RecipeRepository repository;

    private final RecipeStepRepository stepRepository;

    private final RecipeMapper mapper;

    public RecipeReaderAdapter(RecipeRepository repository, RecipeStepRepository stepRepository, RecipeMapper mapper) {
        this.repository = repository;
        this.stepRepository = stepRepository;
        this.mapper = mapper;
    }

    @Override
    public Recipe findById(String id) {
        var steps = stepRepository.findByRecipeId(UUID.fromString(id));
        var recipe = repository.findById(UUID.fromString(id));
        return mapper.toDto(recipe.orElseThrow(), steps);
    }

    @Override
    public Recipes findAll() {
        var steps = stepRepository.findAll();
        return new Recipes(repository.findAll().stream()
                .map(entity -> mapper.toDto(entity, steps.stream()
                        .filter(step -> step.getRecipeId().equals(entity.getId()))
                        .toList()))
                .collect(Collectors.toList()));
    }
}
