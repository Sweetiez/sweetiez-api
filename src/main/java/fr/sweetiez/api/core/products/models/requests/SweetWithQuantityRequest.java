package fr.sweetiez.api.core.products.models.requests;

import java.util.UUID;

public record SweetWithQuantityRequest(UUID sweetId, Integer quantity) {}
