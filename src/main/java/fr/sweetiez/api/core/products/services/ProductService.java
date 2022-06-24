package fr.sweetiez.api.core.products.services;

import fr.sweetiez.api.core.products.models.Product;

import java.util.Collection;
import java.util.UUID;

public interface ProductService {
    Collection<Product> retrieveAllProductsByIds(Collection<UUID> productIds);
}
