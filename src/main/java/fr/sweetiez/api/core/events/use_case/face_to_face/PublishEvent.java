package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;

import java.util.UUID;

public class PublishEvent {

    private final FaceToFaceEvents events;

    public PublishEvent(FaceToFaceEvents events) {
        this.events = events;
    }

    public FaceToFaceEvent publish(UUID id) {
        var existingEvent = events.findById(id).orElseThrow();
        var publishedEvent = FaceToFaceEvent.publish(existingEvent);

        events.save(publishedEvent);

        return publishedEvent;
    }
}
