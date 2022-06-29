package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;

import java.util.UUID;

public class CancelEvent {

    private final StreamingEvents events;

    public CancelEvent(StreamingEvents events) {
        this.events = events;
    }

    public StreamingEvent cancel(UUID id) {
        var event = events.findById(id).orElseThrow();

        var cancelledEvent = StreamingEvent.cancel(event);
        events.save(cancelledEvent);

        return cancelledEvent;
    }
}
