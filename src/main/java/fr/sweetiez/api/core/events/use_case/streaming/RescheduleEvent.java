package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.RescheduleEventRequest;

import java.time.Duration;

public class RescheduleEvent {
    private final Animators animators;
    private final StreamingEvents events;

    public RescheduleEvent(Animators animators, StreamingEvents events) {
        this.animators = animators;
        this.events = events;
    }

    public StreamingEvent reschedule(RescheduleEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();

        var rescheduledEvent =
                StreamingEvent.reschedule(event, request.newStart(), Duration.ofHours(request.newDuration()));

        animators.book(event.animator(), rescheduledEvent.schedule());
        events.save(rescheduledEvent);

        return rescheduledEvent;
    }
}
