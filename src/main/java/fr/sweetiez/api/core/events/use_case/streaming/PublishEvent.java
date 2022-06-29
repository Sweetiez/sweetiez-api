package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;

import java.util.UUID;

public class PublishEvent {

    private final StreamingEvents events;

    public PublishEvent(StreamingEvents events) {
        this.events = events;
    }

    public StreamingEvent publish(UUID id) {
        var existingEvent = events.findById(id).orElseThrow();
        var publishedEvent = StreamingEvent.publish(existingEvent);

        events.save(publishedEvent);

        return publishedEvent;
    }
}
