package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.exception.NoMorePlaceAvailable;
import fr.sweetiez.api.core.events.use_case.models.SubscribeEventRequest;

public class SubscribeEvent {
    private final Events events;
    private final Spaces spaces;
    private final CustomerReader customers;

    public SubscribeEvent(Events events, Spaces spaces, CustomerReader customers) {
        this.events = events;
        this.spaces = spaces;
        this.customers = customers;
    }

    public Event subscribe(SubscribeEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();
        var space = spaces.getInfo(event.getSpace().getId().getSpaceID()).orElseThrow();
        var customer = customers
                .findById(new CustomerId(request.subscriber().toString()))
                .orElseThrow();

        if (event.getSubscribers().size() == space.places()) {
            throw new NoMorePlaceAvailable();
        }

        event.getSubscribers().add(customer);
        events.save(event);

        return event;
    }
}
