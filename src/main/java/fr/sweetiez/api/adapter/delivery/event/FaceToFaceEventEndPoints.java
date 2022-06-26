package fr.sweetiez.api.adapter.delivery.event;

import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.CreateEvent;
import fr.sweetiez.api.core.events.use_case.models.CreateEventRequestDTO;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class FaceToFaceEventEndPoints {
    private final Animators animators;
    private final Spaces spaces;
    private final Events events;

    public FaceToFaceEventEndPoints(Animators animators, Spaces spaces, Events events) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
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
}
