package fr.sweetiez.api.core.trays.models.tray.details;

public record Name(String value) {
    public boolean isValid() {
        return value != null && !value.isEmpty() && value.matches("^[A-Z][ A-zÀ-ú]+$");
    }
}
