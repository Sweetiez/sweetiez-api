package fr.sweetiez.api.core.orders.models.orders.products;

public record Quantity(Integer value) {

    public boolean isValid() {
        return value != null && value >= 1;
    }
}
