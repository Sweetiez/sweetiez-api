package fr.sweetiez.api.core.trays.models.requests;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.trays.models.tray.details.Flavor;

import java.math.BigDecimal;
import java.util.Collection;

public record CreateTrayRequest(
        String name,
        BigDecimal price,
        Collection<Sweet> sweets,
        String description,
        Flavor flavor
) {}
