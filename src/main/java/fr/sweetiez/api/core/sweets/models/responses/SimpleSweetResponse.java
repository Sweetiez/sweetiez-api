package fr.sweetiez.api.core.sweets.models.responses;

import java.math.BigDecimal;
import java.util.Collection;

public record SimpleSweetResponse(
        String id,
        String name,
        BigDecimal price,
        String description,
        Collection<String> images,
        String rating
) {}
