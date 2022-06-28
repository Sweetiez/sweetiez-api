package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.exception.AnyAnimatorFoundException;
import fr.sweetiez.api.core.events.use_case.exception.AnySpaceFoundException;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.CreateEventRequestDTO;

import java.time.Duration;

public class CreateEvent {
    private final Animators animators;
    private final Spaces spaces;
    private final FaceToFaceEvents events;

    public CreateEvent(Animators animators, Spaces spaces, FaceToFaceEvents events) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
    }

    public FaceToFaceEvent create(CreateEventRequestDTO request) {
        Animator animator = animators.findById(request.animatorId())
                .orElseThrow(AnyAnimatorFoundException::new);
        Space space = spaces.findById(request.spaceId())
                .orElseThrow(AnySpaceFoundException::new);

        FaceToFaceEvent event = new FaceToFaceEvent(request.title(), request.description(), animator, space,
                request.startDateTime(), Duration.ofHours(request.duration()));

        animators.book(event.getAnimator(), event.getSchedule());
        spaces.book(space, event.getSchedule());
        events.save(event);

        return event;
    }
}
