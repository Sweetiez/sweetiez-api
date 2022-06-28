package fr.sweetiez.api.adapter.delivery.event;

import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.*;
import fr.sweetiez.api.core.events.use_case.models.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

public class FaceToFaceEventEndPoints {
    private final Animators animators;
    private final Spaces spaces;
    private final Events events;
    private final CustomerReader customers;

    public FaceToFaceEventEndPoints(Animators animators, Spaces spaces, Events events, CustomerReader customers) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
        this.customers = customers;
    }

    public ResponseEntity<Object> createEvent(CreateEventRequestDTO request) {
        try {
            var useCase = new CreateEvent(animators, spaces, events);
            var event = useCase.create(request);

            return ResponseEntity.created(URI.create("/events/face-to-face/" + event.getId().getEventId())).build();
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Event> publishEvent(UUID id) {
        try {
            var useCase = new PublishEvent(events);
            var event = useCase.publish(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Event> cancelEvent(UUID id) {
        try {
            var useCase = new CancelEvent(events);
            var event = useCase.cancel(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Event> rescheduleEvent(RescheduleEventRequest request) {
        try {
            var useCase = new RescheduleEvent(animators, spaces, events);
            var event = useCase.reschedule(request);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            exception.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Event> subscribeEvent(SubscribeEventRequest request) {
        try {
            var useCase = new SubscribeEvent(events, spaces, customers);
            var event = useCase.subscribe(request);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<EventResponse>> retrieveAllPublished() {
        try {
            var useCase = new RetrieveEvents(events, spaces, customers);
            var event = useCase.retrievePublished();

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<EventAdminResponse>> retrieveAll() {
        try {
            var useCase = new RetrieveEvents(events, spaces, customers);
            var event = useCase.retrieveAll();

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
