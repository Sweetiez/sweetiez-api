package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.RecipeMapper;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepRepository;

public class RecipeReaderAdapter implements RecipeReader {

    private final RecipeRepository repository;

    private final RecipeStepRepository stepRepository;

    private final RecipeMapper mapper;

    public RecipeReaderAdapter(RecipeRepository repository, RecipeStepRepository stepRepository, RecipeMapper mapper) {
        this.repository = repository;
        this.stepRepository = stepRepository;
        this.mapper = mapper;
    }
}
