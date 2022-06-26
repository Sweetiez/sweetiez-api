package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;

import java.util.UUID;

public class PublishEvent {

    private final Events events;

    public PublishEvent(Events events) {
        this.events = events;
    }

    public Event publish(UUID id) {

        System.out.println(id);

        var existingEvent = events.findById(id).orElseThrow();


        var publishedEvent = Event.publish(existingEvent);

        events.save(publishedEvent);

        return publishedEvent;
    }
}
