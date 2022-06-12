package fr.sweetiez.api.core.products.models.requests;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public record UpdateSweetRequest(
        UUID id,
        String name,
        BigDecimal price,
        String description,
        Collection<String> images,
        Collection<UUID> ingredients,
        Highlight highlight,
        State state,
        Flavor flavor
) {}
