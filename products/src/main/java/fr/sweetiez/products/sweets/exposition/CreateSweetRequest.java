package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.Flavor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateSweetRequest {
    private final String name;
    private final Set<String> ingredients;
    private final String description;
    private final Flavor type;
    private final BigDecimal price;
    private final UUID creator;
}
