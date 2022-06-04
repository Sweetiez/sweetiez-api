package fr.sweetiez.api.core.orders.models.orders.products;

import fr.sweetiez.api.core.sweets.models.sweet.details.Price;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, UUID productId, String name, ProductType type, Quantity quantity, Price unitPrice) {

    public Product(String productId, String name, ProductType type, Integer quantity, double price) {
        this(
            UUID.randomUUID(),
            UUID.fromString(productId),
            name,
            type,
            new Quantity(quantity),
            new Price(BigDecimal.valueOf(price))
        );
    }

    public boolean isValid() {
        return productId != null
                && name != null
                && !name.isEmpty()
                && type != null
                && quantity.isValid()
                && unitPrice.isValid();
    }
}


