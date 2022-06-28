package fr.sweetiez.api.core.orders.models.orders;

import java.util.UUID;

public record OrderId(UUID value) {

    public OrderId() {
        this(UUID.randomUUID());
    }

    public OrderId(String value) {
        this(UUID.fromString(value));
    }

    public OrderId(UUID value) {
        this.value = value;
    }
}
