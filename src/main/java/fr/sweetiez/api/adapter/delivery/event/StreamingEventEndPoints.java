package fr.sweetiez.api.adapter.delivery.event;

import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.use_case.streaming.*;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.CreateStreamingEventRequestDTO;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.RescheduleEventRequest;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.SubscribeEventRequest;
import fr.sweetiez.api.core.events.use_case.streaming.models.responses.EventAdminResponse;
import fr.sweetiez.api.core.events.use_case.streaming.models.responses.EventResponse;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

public class StreamingEventEndPoints {
    private final Animators animators;
    private final StreamingEvents events;
    private final CustomerReader customers;

    public StreamingEventEndPoints(Animators animators, StreamingEvents events, CustomerReader customers) {
        this.animators = animators;
        this.events = events;
        this.customers = customers;
    }

    public ResponseEntity<Object> createEvent(CreateStreamingEventRequestDTO request) {
        try {
            var useCase = new CreateEvent(animators, events);
            var event = useCase.create(request);

            return ResponseEntity.created(URI.create("/events/streaming/" + event.id().eventId())).build();
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<StreamingEvent> publishEvent(UUID id) {
        try {
            var useCase = new PublishEvent(events);
            var event = useCase.publish(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<StreamingEvent> cancelEvent(UUID id) {
        try {
            var useCase = new CancelEvent(events);
            var event = useCase.cancel(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<StreamingEvent> rescheduleEvent(RescheduleEventRequest request) {
        try {
            var useCase = new RescheduleEvent(animators, events);
            var event = useCase.reschedule(request);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            exception.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<StreamingEvent> subscribeEvent(SubscribeEventRequest request) {
        try {
            var useCase = new SubscribeEvent(events, customers);
            var event = useCase.subscribe(request);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<EventResponse>> retrieveAllPublished() {
        try {
            var useCase = new RetrieveEvents(events, customers);
            var event = useCase.retrievePublished();

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<EventAdminResponse>> retrieveAll() {
        try {
            var useCase = new RetrieveEvents(events, customers);
            var event = useCase.retrieveAll();

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<EventResponse>> retrieveMyEvents(UUID id) {
        try {
            var useCase = new RetrieveEvents(events, customers);
            var event = useCase.retrieveMyEvents(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> participate(AttendMasterClassRequest request) {
        var useCase = new AttendMasterclass(events);
        var canAttend = useCase.attendMasterclass(request);

        var response =
                (canAttend) ? ResponseEntity.ok() : ResponseEntity.badRequest();

        return response.build();
    }
}
