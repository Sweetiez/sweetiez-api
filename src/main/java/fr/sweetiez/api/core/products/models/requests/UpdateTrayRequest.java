package fr.sweetiez.api.core.products.models.requests;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateTrayRequest(
        UUID id,
        String name,
        BigDecimal price,
        String description,
        List<String> images,
        List<SweetWithQuantityRequest> sweets,
        Highlight highlight,
        State state,
        Flavor flavor
) {}
