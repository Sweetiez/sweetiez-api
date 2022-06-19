package fr.sweetiez.api.core.ingredients.services;

import fr.sweetiez.api.adapter.gateways.allergen.models.HealthLabel;
import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.HealthPropertyType;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.ports.IngredientApi;
import fr.sweetiez.api.core.ingredients.ports.Ingredients;
import fr.sweetiez.api.core.ingredients.ports.TranslatorApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class IngredientService {

    private final TranslatorApi translator;
    private final IngredientApi ingredientApi;
    private final Ingredients ingredientRepository;

    public IngredientService(TranslatorApi translator, IngredientApi ingredientApi,
                             Ingredients ingredientRepository) {
        this.translator = translator;
        this.ingredientApi = ingredientApi;
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient create(String name) {
        var optionalIngredient = ingredientRepository.findByName(name);

        if (optionalIngredient.isPresent()) {
            throw new IngredientAlreadyExistsException();
        }

        var translatedName = translator.translate(name, "fr", "en");
        var ingredientId = ingredientApi.findIdByName(translatedName);
        var healthLabels = ingredientApi.getHealthLabelsOf(ingredientId);

        var healthProperties = ingredientRepository.retrieveAllHealthProperties();
        var ingredientProperties = addDietsToIngredient(healthLabels, healthProperties);
        var allergens = addAllergensToIngredient(healthLabels, healthProperties);

        ingredientProperties.addAll(allergens);

        return ingredientRepository.saveIngredient(new Ingredient(null, name, ingredientProperties));
    }

    public Collection<Ingredient> retrieveAllById(Collection<UUID> ingredients) {
        return ingredientRepository.findAllById(ingredients);
    }
    private Collection<HealthProperty> addDietsToIngredient(Collection<HealthLabel> healthLabels, Collection<HealthProperty> healthProperties) {
        var diets = HealthLabel.getDiets(healthLabels);
        var ingredientProperties = new ArrayList<HealthProperty>();

        for (var diet : diets) {
            var optionalDietProperty = healthProperties.stream()
                    .filter(healthProperty -> healthProperty.type().equals(HealthPropertyType.DIET))
                    .filter(healthProperty -> healthProperty.name().equals(diet))
                    .findFirst();

            HealthProperty dietToAdd;

            if (optionalDietProperty.isEmpty()) {
                // add to database if property does not exist
                var dietProperty = new HealthProperty(null, diet, HealthPropertyType.DIET);
                dietToAdd = ingredientRepository.saveHealthProperty(dietProperty);
                healthProperties.add(dietToAdd);
            }
            else {
                dietToAdd = optionalDietProperty.get();
            }

            ingredientProperties.add(dietToAdd);
        }

        return ingredientProperties;
    }

    private Collection<HealthProperty> addAllergensToIngredient(Collection<HealthLabel> healthLabels, Collection<HealthProperty> healthProperties) {
        var allergensToCompare = new ArrayList<HealthProperty>();
        var allergens = HealthLabel.getAllergens(healthLabels);

        for (var allergen : allergens) {
            var optionalAllergenProperty = healthProperties.stream()
                    .filter(healthProperty -> healthProperty.type().equals(HealthPropertyType.ALLERGEN))
                    .filter(healthProperty -> healthProperty.name().equals(allergen))
                    .findFirst();

            HealthProperty allergenToAdd;

            if (optionalAllergenProperty.isEmpty()) {
                var allergenProperty = new HealthProperty(null, allergen, HealthPropertyType.ALLERGEN);
                allergenToAdd = ingredientRepository.saveHealthProperty(allergenProperty);
                healthProperties.add(allergenToAdd);
            }
            else {
                allergenToAdd = optionalAllergenProperty.get();
            }
            allergensToCompare.add(allergenToAdd);
        }

        var allergensStaying = healthProperties.stream()
                .filter(healthProperty -> healthProperty.type().equals(HealthPropertyType.ALLERGEN))
                .collect(Collectors.toList());

        allergensStaying.removeAll(allergensToCompare);

        return allergensStaying;
    }
}
