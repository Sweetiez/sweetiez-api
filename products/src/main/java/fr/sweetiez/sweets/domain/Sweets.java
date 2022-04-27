package fr.sweetiez.sweets.domain;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface Sweets {
    Optional<UUID> save(Sweet sweet, UUID creator);
    Set<Sweet> all();
    Optional<Sweet> findByID(String id);
    void update(Sweet publishedSweet, UUID randomUUID);
}
