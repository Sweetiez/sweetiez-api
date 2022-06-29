package fr.sweetiez.api.core.events.events.streaming_event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StreamingEvents {

    void save(StreamingEvent event);
    Optional<StreamingEvent> findById(UUID id);
    List<StreamingEvent> findAllPublished();
    List<StreamingEvent> findAll();
}
