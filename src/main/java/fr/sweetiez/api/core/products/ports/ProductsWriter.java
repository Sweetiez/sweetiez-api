package fr.sweetiez.api.core.products.ports;

import fr.sweetiez.api.core.products.models.Product;

public interface ProductsWriter<T extends Product> {
    T save(T product);
}
