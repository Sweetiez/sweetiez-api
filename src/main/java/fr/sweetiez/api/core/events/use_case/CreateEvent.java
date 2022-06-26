package fr.sweetiez.api.core.events.use_case;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.exception.AnyAnimatorFoundException;
import fr.sweetiez.api.core.events.use_case.exception.AnySpaceFoundException;
import fr.sweetiez.api.core.events.use_case.models.CreateEventRequestDTO;

public class CreateEvent {
    private final Animators animators;
    private final Spaces spaces;
    private final Events events;

    public CreateEvent(Animators animators, Spaces spaces, Events events) {
        this.animators = animators;
        this.spaces = spaces;
        this.events = events;
    }

    public Event create(CreateEventRequestDTO createEventRequestDTO) {
        Animator animator = animators.findById(createEventRequestDTO.getAnimatorId())
                .orElseThrow(AnyAnimatorFoundException::new);
        Space space = spaces.findById(createEventRequestDTO.getSpaceId())
                .orElseThrow(AnySpaceFoundException::new);

        Event event = new Event(createEventRequestDTO.getTitle(), animator, space,
                createEventRequestDTO.getStartDateTime(), createEventRequestDTO.getDuration());

        animators.book(event.getAnimator(), event.getSchedule());
        spaces.book(space, event.getSchedule());
        events.save(event);

        return event;
    }
}
