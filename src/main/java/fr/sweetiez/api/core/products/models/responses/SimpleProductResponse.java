package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.products.models.Product;

import java.util.Collection;
import java.util.UUID;

public record SimpleProductResponse(
        UUID id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        double rating,
        ProductType type
)
{
    public SimpleProductResponse(Product product, ProductType productType) {
        this(
                product.id().value(),
                product.name().value(),
                product.price().unitPrice().doubleValue(),
                product.description().shortContent(),
                product.details().characteristics().flavor().name(),
                product.details().images(),
                product.details().valuation().globalMark(),
                productType
        );
    }
}
