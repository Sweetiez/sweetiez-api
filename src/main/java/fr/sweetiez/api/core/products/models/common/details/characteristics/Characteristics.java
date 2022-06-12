package fr.sweetiez.api.core.products.models.common.details.characteristics;

public record Characteristics(Highlight highlight, State state, Flavor flavor) {
    public boolean isValid() {
        return highlight != null && state != null  && flavor != null;
    }
}
