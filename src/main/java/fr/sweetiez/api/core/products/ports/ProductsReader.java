package fr.sweetiez.api.core.products.ports;

import fr.sweetiez.api.core.products.models.Product;
import fr.sweetiez.api.core.products.models.common.ProductID;

import java.util.Collection;
import java.util.Optional;

public interface ProductsReader<T extends Product> {
    Optional<T> findById(ProductID id);
    Collection<T> findAllPublished();
    Collection<T> findAll();
}
