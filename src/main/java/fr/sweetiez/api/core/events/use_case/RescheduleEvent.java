package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.models.RescheduleEventRequest;

public class RescheduleEvent {
    private final Animators animators;
    private final Spaces spaces;
    private final Events events;

    public RescheduleEvent(Animators animators, Spaces spaces, Events events) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
    }

    public Event reschedule(RescheduleEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();

        var rescheduledEvent = Event.reschedule(event, request.newStart(), request.newDuration());

        animators.book(event.getAnimator(), rescheduledEvent.getSchedule());
        spaces.book(event.getSpace(), rescheduledEvent.getSchedule());
        events.save(rescheduledEvent);

        return rescheduledEvent;
    }
}
