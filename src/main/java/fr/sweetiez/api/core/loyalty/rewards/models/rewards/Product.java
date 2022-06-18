package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

public record Product(String id, String name) {

    public boolean isValid() {
        return id != null && !id.isEmpty() && name != null && !name.isEmpty();
    }
}
