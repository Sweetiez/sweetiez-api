package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Product;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;

import java.util.Collection;
import java.util.UUID;

public record SimpleProductResponse(
        UUID id,
        String name,
        double price,
        int unitPerPackage,
        String description,
        String flavor,
        Collection<String> images,
        double rating,
        Highlight highlight
)
{
    public SimpleProductResponse(Product product) {
        this(
                product.id().value(),
                product.name().value(),
                product.price().packaged().doubleValue(),
                product.price().unitPerPackage(),
                product.description().shortContent(),
                product.details().characteristics().flavor().name(),
                product.details().images(),
                product.details().valuation().globalMark(),
                product.details().characteristics().highlight()
        );
    }
}
