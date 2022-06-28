package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;

import java.util.UUID;

public class CancelEvent {

    private final FaceToFaceEvents events;


    public CancelEvent(FaceToFaceEvents events) {
        this.events = events;
    }

    public FaceToFaceEvent cancel(UUID id) {
        var event = events.findById(id).orElseThrow();

        var cancelledEvent = FaceToFaceEvent.cancel(event);
        events.save(cancelledEvent);

        return cancelledEvent;
    }
}
