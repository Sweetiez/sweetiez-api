package fr.sweetiez.api.core.recipes.models.recipes.details;

public record Title(String value) {

    public boolean isValid() {
        return value != null && !value.isEmpty() && value.matches("^[A-Z][ A-zÀ-ú]+$");
    }

}
