package fr.sweetiez.api.core.loyalty.rewards.models.rewards;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;

public record Product(String id, String name, ProductType type, String image) {

    public Product(String id, ProductType type) {
        this(id, "", type, "");
    }

    public boolean isValid() {
        return !id.isEmpty() && !name.isEmpty() && type != null && !image.isEmpty();
    }
}
