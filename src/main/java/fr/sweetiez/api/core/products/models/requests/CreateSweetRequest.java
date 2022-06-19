package fr.sweetiez.api.core.products.models.requests;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public record CreateSweetRequest(
        String name,
        BigDecimal price,
        Integer unitPerPackage,
        Collection<UUID> ingredients,
        String description,
        Flavor flavor) {}
