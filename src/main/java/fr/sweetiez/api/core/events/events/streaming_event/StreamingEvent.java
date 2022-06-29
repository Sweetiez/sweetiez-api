package fr.sweetiez.api.core.events.events.streaming_event;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.events.EventID;
import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.core.events.schedule.Schedule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record StreamingEvent(
        EventID id,
        String title,
        String description,
        Animator animator,
        Schedule schedule,
        StatusEvent status,
        int places,
        List<Customer> subscribers
) {
    public static StreamingEvent create(String title, String description, Animator animator, LocalDateTime startDateTime, Duration duration, int places) {
        Schedule eventSchedule = new Schedule(startDateTime, duration);
        animator.book(eventSchedule);

        return new StreamingEvent(
                new EventID(UUID.randomUUID()),
                title,
                description,
                animator,
                eventSchedule,
                StatusEvent.CREATED,
                places,
                List.of());
    }

    public static StreamingEvent publish(StreamingEvent event) {
        return new StreamingEvent(event, StatusEvent.PUBLISHED);
    }

    public static StreamingEvent cancel(StreamingEvent event) {
        return new StreamingEvent(event, StatusEvent.CANCELLED);
    }

    public static StreamingEvent reschedule(StreamingEvent event, LocalDateTime start, Duration duration) {
        Schedule eventSchedule = new Schedule(start, duration);
        event.animator.reschedule(event.schedule, eventSchedule);

        return new StreamingEvent(event, eventSchedule);
    }

    public StreamingEvent(StreamingEventDto dto) {
        this(
                new EventID(dto.id()),
                dto.title(),
                dto.description(),
                dto.animator(),
                dto.schedule(),
                dto.status(),
                dto.places(),
                dto.subscribers()
        );
    }

    private StreamingEvent(StreamingEvent event, StatusEvent newStatus) {
        this(
                event.id,
                event.title,
                event.description,
                event.animator,
                event.schedule,
                newStatus,
                event.places,
                event.subscribers
        );
    }

    private StreamingEvent(StreamingEvent event, Schedule schedule) {
        this(
                event.id,
                event.title,
                event.description,
                event.animator,
                schedule,
                event.status,
                event.places,
                event.subscribers
        );
    }
}
