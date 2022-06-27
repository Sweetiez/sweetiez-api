package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;

import java.util.UUID;

public class CancelEvent {

    private final Events events;


    public CancelEvent(Events events) {
        this.events = events;
    }

    public Event cancel(UUID id) {
        var optionalEvent = events.findById(id).orElseThrow();

        var cancelledEvent = Event.cancel(optionalEvent);
        events.save(cancelledEvent);

        return cancelledEvent;
    }
}
