package fr.sweetiez.api.core.orders.models.requests;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;

import java.util.UUID;

public record ProductOrderRequest(UUID productId, ProductType type, Integer quantity) {
}
