package fr.sweetiez.api.core.products.models.requests;


import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;

import java.util.UUID;

public record PublishProductRequest(UUID id, Highlight highlight) {}
