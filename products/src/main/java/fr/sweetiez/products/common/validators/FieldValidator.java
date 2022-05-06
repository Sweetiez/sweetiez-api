package fr.sweetiez.products.common.validators;

import java.util.Set;

public interface FieldValidator {
    boolean hasErrors();
    Set<InvalidFieldException> getErrors();
}
