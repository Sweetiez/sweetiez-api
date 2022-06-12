package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Product;
import fr.sweetiez.api.core.products.models.Sweet;

import java.util.Collection;
import java.util.UUID;

public record SimpleProductResponse(
        UUID id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        double rating
)
{
    public SimpleProductResponse(Product product) {
        this(
                product.id().value(),
                product.name().value(),
                product.price().value().doubleValue(),
                product.description().shortContent(),
                product.details().characteristics().flavor().name(),
                product.details().images(),
                product.details().valuation().globalMark()
        );
    }
}
