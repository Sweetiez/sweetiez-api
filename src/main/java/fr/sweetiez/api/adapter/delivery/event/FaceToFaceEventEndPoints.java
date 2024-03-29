package fr.sweetiez.api.adapter.delivery.event;

import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.space.SpaceDTO;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.face_to_face.*;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

public class FaceToFaceEventEndPoints {
    private final Animators animators;
    private final Spaces spaces;
    private final FaceToFaceEvents events;
    private final CustomerReader customers;
    private final SpaceService spaceService;

    public FaceToFaceEventEndPoints(Animators animators, Spaces spaces, FaceToFaceEvents events,
                                    CustomerReader customers, SpaceService spaceService) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
        this.customers = customers;
        this.spaceService = spaceService;
    }

    public ResponseEntity<Object> createEvent(CreateEventRequestDTO request) {
        try {
            var useCase = new CreateEvent(animators, spaces, events);
            var event = useCase.create(request);

            return ResponseEntity.created(URI.create("/events/face-to-face/" + event.getId().eventId())).build();
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<FaceToFaceEvent> publishEvent(UUID id) {
        try {
            var useCase = new PublishEvent(events);
            var event = useCase.publish(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<FaceToFaceEvent> cancelEvent(UUID id) {
        try {
            var useCase = new CancelEvent(events);
            var event = useCase.cancel(id);

            return ResponseEntity.ok(event);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<FaceToFaceEvent> rescheduleEvent(RescheduleEventRequest request) {
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

    public ResponseEntity<FaceToFaceEvent> subscribeEvent(SubscribeEventRequest request) {
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

    public ResponseEntity<Collection<EventResponse>> retrieveMyEvents(UUID userId) {
        try {
            var useCase = new RetrieveEvents(events, spaces, customers);
            var event = useCase.retrieveMyEvents(userId);

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

    public ResponseEntity<SpaceDTO> createSpace(CreateSpaceRequest request) {
        try {
            var space = spaceService.create(request);
            return ResponseEntity.ok(space);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<SpaceDTO>> retrieveSpaces() {
        return ResponseEntity.ok(spaceService.retrieveSpaces());
    }

    public ResponseEntity<Collection<AnimatorAdminResponse>> retrieveAnimators() {
        return ResponseEntity.ok(spaceService.retrieveAnimators());
    }
}
