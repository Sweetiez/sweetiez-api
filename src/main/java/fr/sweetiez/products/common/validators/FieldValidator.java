package fr.sweetiez.products.common.validators;

import java.util.Set;

public interface FieldValidator <T> {
    boolean hasErrors(T product);
    Set<InvalidFieldException> getErrors();
}
