package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.RescheduleEventRequest;

import java.time.Duration;

public class RescheduleEvent {
    private final Animators animators;
    private final Spaces spaces;
    private final FaceToFaceEvents events;

    public RescheduleEvent(Animators animators, Spaces spaces, FaceToFaceEvents events) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
    }

    public FaceToFaceEvent reschedule(RescheduleEventRequest request) {
        var event = events.findById(request.eventId()).orElseThrow();

        var rescheduledEvent =
                FaceToFaceEvent.reschedule(event, request.newStart(), Duration.ofHours(request.newDuration()));

        animators.book(event.getAnimator(), rescheduledEvent.getSchedule());
        spaces.book(event.getSpace(), rescheduledEvent.getSchedule());
        events.save(rescheduledEvent);

        return rescheduledEvent;
    }
}
