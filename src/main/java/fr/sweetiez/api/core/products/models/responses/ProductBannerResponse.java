package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Product;

import java.util.UUID;


public record ProductBannerResponse(
        UUID id,
        String name,
        String images) {

    public ProductBannerResponse(Product product) {
        this(
                product.id().value(),
                product.name().value(),
                product.details().images().toArray()[0].toString()
        );
    }
}
