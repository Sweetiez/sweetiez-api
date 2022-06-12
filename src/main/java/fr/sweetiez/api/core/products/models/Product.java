package fr.sweetiez.api.core.products.models;

import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;

import java.util.Objects;

public abstract class Product {
    protected final ProductID id;
    protected final Name name;
    protected final Description description;
    protected final Price price;
    protected final Details details;

    public Product(ProductID id, Name name, Description description, Price price, Details details)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.details = details;
    }

    public abstract Product publish(Highlight highlight);

    public abstract Product unpublish();

    protected boolean isValid() {
        return name != null && name.isValid()
                && description != null && description.isValid()
                && price != null && price.isValid()
                && details != null && details.isValid();
    }

    public ProductID id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public Description description() {
        return description;
    }

    public Price price() {
        return price;
    }

    public Details details() {
        return details;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && name.equals(product.name) && description.equals(product.description) && price.equals(product.price) && details.equals(product.details);
    }

    public int hashCode() {
        return Objects.hash(id, name, description, price, details);
    }
}
