package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.use_case.models.RescheduleEventRequest;

public class RescheduleEvent {
    private final Events events;

    public RescheduleEvent(Events events) {
        this.events = events;
    }

    public Event reschedule(RescheduleEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();

        var rescheduledEvent = Event.reschedule(event, request.newStart(), request.newDuration());
        events.save(rescheduledEvent);

        return rescheduledEvent;
    }
}
