package fr.sweetiez.api.core.trays.models.requests;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.trays.models.tray.details.Flavor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public record UpdateTrayRequest(
        String id,
        String name,
        BigDecimal price,
        String description,
        Collection<String> images,
        Collection<Sweet> sweets,
        String highlight,
        String state,
        Flavor flavor,
        double rating
) {
}
