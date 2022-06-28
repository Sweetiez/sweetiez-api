package fr.sweetiez.api.adapter.repository.ingredients;

import fr.sweetiez.api.adapter.shared.IngredientMapper;
import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.ports.Ingredients;
import fr.sweetiez.api.infrastructure.repository.ingredients.HealthPropertyRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class IngredientRepositoryAdapter implements Ingredients {

    private final fr.sweetiez.api.infrastructure.repository.ingredients.IngredientRepository repository;
    private final HealthPropertyRepository healthPropertyRepository;
    private final IngredientMapper mapper;

    public IngredientRepositoryAdapter(
            fr.sweetiez.api.infrastructure.repository.ingredients.IngredientRepository repository,
            HealthPropertyRepository healthPropertyRepository,
            IngredientMapper mapper)
    {
        this.repository = repository;
        this.healthPropertyRepository = healthPropertyRepository;
        this.mapper = mapper;
    }

    public Optional<Ingredient> findByName(String name) {
        return repository.findByName(name).map(mapper::toDto);
    }

    public Collection<HealthProperty> retrieveAllHealthProperties() {
        return healthPropertyRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public HealthProperty saveHealthProperty(HealthProperty property) {
        return mapper.toDto(healthPropertyRepository.save(mapper.toEntity(property)));
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return mapper.toDto(repository.save(mapper.toEntity(ingredient)));
    }

    public Collection<Ingredient> findAllById(Collection<UUID> ingredients) {
        return repository.findAllById(ingredients)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Collection<Ingredient> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
