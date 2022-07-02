package fr.sweetiez.api.core.orders.models.requests;

import java.util.UUID;

public record VerifyPurchaseRequest(
        String email,
        UUID productId
) {
}
