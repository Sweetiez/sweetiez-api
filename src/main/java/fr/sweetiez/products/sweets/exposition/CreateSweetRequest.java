package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.Flavor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateSweetRequest(
        String name,
        Set<String> ingredients,
        String description,
        Flavor flavor,
        BigDecimal price, UUID creator) {}
