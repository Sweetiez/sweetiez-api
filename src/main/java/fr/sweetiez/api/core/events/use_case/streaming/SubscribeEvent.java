package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.use_case.exception.NoMorePlaceAvailable;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.SubscribeEventRequest;

public class SubscribeEvent {
    private final StreamingEvents events;
    private final CustomerReader customers;

    public SubscribeEvent(StreamingEvents events, CustomerReader customers) {
        this.events = events;
        this.customers = customers;
    }

    public StreamingEvent subscribe(SubscribeEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();

        if (event.subscribers().size() == event.places()) {
            throw new NoMorePlaceAvailable();
        }

        var customerId = request.subscriber().toString();
        var customer = customers.findById(new CustomerId(customerId)).orElseThrow();

        event.subscribers().add(customer);
        events.save(event);

        return event;
    }
}
