package fr.sweetiez.api.core.events.event;

import java.util.Optional;
import java.util.UUID;

public interface Events {

    void save(Event event);
    Optional<Event> findById(UUID id);
}
