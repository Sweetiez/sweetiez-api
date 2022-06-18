package fr.sweetiez.api.core.products.models.requests;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;

import java.math.BigDecimal;
import java.util.List;

public record CreateTrayRequest(
        String name,
        BigDecimal price,
        List<SweetWithQuantityRequest> sweets,
        String description,
        Flavor flavor
) {}
