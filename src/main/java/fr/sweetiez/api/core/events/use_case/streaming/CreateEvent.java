package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.use_case.exception.AnyAnimatorFoundException;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.CreateStreamingEventRequestDTO;

import java.time.Duration;

public class CreateEvent {
    private final Animators animators;
    private final StreamingEvents events;

    public CreateEvent(Animators animators, StreamingEvents events) {
        this.animators = animators;
        this.events = events;
    }

    public StreamingEvent create(CreateStreamingEventRequestDTO request) {
        Animator animator = animators.findById(request.animatorId())
                .orElseThrow(AnyAnimatorFoundException::new);

        StreamingEvent event = StreamingEvent.create(request.title(), request.description(), animator,
                request.startDateTime(), Duration.ofHours(request.duration()), request.places());

        animators.book(event.animator(), event.schedule());
        events.save(event);

        return event;
    }
}
