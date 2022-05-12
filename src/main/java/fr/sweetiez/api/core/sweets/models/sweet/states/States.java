package fr.sweetiez.api.core.sweets.models.sweet.states;

public record States(Highlight highlight, State state) {
    public boolean isValid() {
        return highlight != null && state != null;
    }
}
