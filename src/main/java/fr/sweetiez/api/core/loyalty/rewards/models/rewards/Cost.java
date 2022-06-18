package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

public record Cost(Integer value) {

    public boolean isValid() {
        return value != null && value > 0;
    }
}
