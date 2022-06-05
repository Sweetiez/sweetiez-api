package fr.sweetiez.api.core.trays.models.tray.states;

public record States(Highlight highlight, State state) {
    public boolean isValid() {
        return highlight != null && state != null;
    }
}
