package fr.sweetiez.api.core.products.models.common;

public record Name(String value) {
    public boolean isValid() {
        return value != null && !value.isEmpty() && value.matches("^[A-Z][ A-zÀ-ú]+$");
    }
}
