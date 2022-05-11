package fr.sweetiez.api.core.sweets.models.sweet.details;

public record Name(String value) {
    public boolean isValid() {
        return value != null && !value.isEmpty() && value.matches("^[A-Z][ A-Za-z]+$");
    }
}
