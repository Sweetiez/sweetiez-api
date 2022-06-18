package fr.sweetiez.api.core.products.models.responses;


import fr.sweetiez.api.core.products.models.Product;

import java.util.Collection;
import java.util.UUID;

public record AdminSimpleProductResponse(
        UUID id,
        String name,
        double price,
        String status,
        String description,
        Collection<String> images,
        String highlight
) {
    public AdminSimpleProductResponse(Product product) {
        this(
                product.id().value(),
                product.name().value(),
                product.price().unitPrice().doubleValue(),
                product.details().characteristics().state().toString(),
                product.description().shortContent(),
                product.details().images(),
                product.details().characteristics().highlight().toString()
        );
    }
}
