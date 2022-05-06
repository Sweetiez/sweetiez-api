package fr.sweetiez.products.sweets.domain;

import fr.sweetiez.products.common.validators.FieldValidator;
import fr.sweetiez.products.common.validators.InvalidFieldException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SweetValidator implements FieldValidator {

    private final Sweet sweet;
    private final Set<InvalidFieldException> errors;

    public SweetValidator(Sweet sweet) {
        this.sweet = sweet;
        this.errors = new HashSet<>();
    }

    public boolean hasErrors() {
        checkNameValidity(sweet.getName());
        checkPriceValidity(sweet.getPrice());
        checkIngredientsValidity(sweet.getIngredients());

        return errors.size() > 0;
    }

    public Set<InvalidFieldException> getErrors() {
        return errors;
    }

    private void checkNameValidity(String name) {
        if (name == null || name.isEmpty() || !name.matches("^[A-Z][ A-Za-z]+$")) {
            errors.add(new InvalidFieldException("Name should only contain letters and space, starting by a capital letter"));
        }
    }

    private void checkPriceValidity(BigDecimal price) {
        if (price == null || price.doubleValue() <= 0.) {
            errors.add(new InvalidFieldException("Price should be higher than zero"));
        }
    }

    private void checkIngredientsValidity(Set<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            errors.add(new InvalidFieldException("Ingredients should not be null nor empty"));
            return;
        }

        for (var ingredient : ingredients) {
            if (ingredient == null || ingredient.isEmpty()) {
                errors.add(new InvalidFieldException("Any ingredient cannot be null nor empty"));
                break;
            }
        }
    }
}
