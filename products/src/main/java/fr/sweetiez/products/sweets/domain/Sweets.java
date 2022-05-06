package fr.sweetiez.products.sweets.domain;

import fr.sweetiez.products.common.Highlight;

import java.util.Set;
import java.util.UUID;

public interface Sweets {
    Sweet create(Sweet sweet, UUID creator);
    Set<Sweet> all();
    Sweet findById(UUID id);
    Sweet publish(String sweetId, Highlight priority, UUID employee);
}
