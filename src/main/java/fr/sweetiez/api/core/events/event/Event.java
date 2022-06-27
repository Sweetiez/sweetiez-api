package fr.sweetiez.api.core.events.event;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Event {
    private final EventID id;
    private final String title;
    private final Animator animator;
    private final Schedule schedule;
    private final StatusEvent status;
    private final Space space;
    private final List<Customer> subscribers;

    public Event(String title, Animator animator, Space space, LocalDateTime startDateTime, Duration duration) {
        Schedule eventSchedule = new Schedule(startDateTime, duration);

        animator.book(eventSchedule);
        space.book(eventSchedule);

        this.id = new EventID(UUID.randomUUID());
        this.title = title;
        this.status = StatusEvent.CREATED;
        this.animator = animator;
        this.schedule = eventSchedule;
        this.space = space;
        this.subscribers = List.of();
    }

    public Event(EventDto dto) {
        this.id = new EventID(dto.id());
        this.title = dto.title();
        this.animator = dto.animator();
        this.schedule = dto.schedule();
        this.status = dto.status();
        this.space = dto.space();
        this.subscribers = dto.subscribers();
    }

    private Event(Event event, StatusEvent status) {
        this.id = event.id;
        this.title = event.title;
        this.status = status;
        this.animator = event.animator;
        this.schedule = event.schedule;
        this.space = event.space;
        this.subscribers = event.subscribers;
    }

    public Event(Event event, LocalDateTime newStart, Duration newDuration) {
        Schedule eventSchedule = new Schedule(newStart, newDuration);

        event.animator.book(eventSchedule);
        event.space.book(eventSchedule);

        this.id = event.id;
        this.title = event.title;
        this.status = StatusEvent.PUBLISHED;
        this.animator = event.animator;
        this.schedule = eventSchedule;
        this.space = event.space;
        this.subscribers = List.of();
    }

    public static Event publish(Event event) {
        return new Event(event, StatusEvent.PUBLISHED);
    }

    public static Event cancel(Event event) {
        return new Event(event, StatusEvent.CANCELLED);
    }

    public static Event reschedule(Event event, LocalDateTime start, Duration duration) {
        return new Event(event, start, duration);
    }

    public EventID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Animator getAnimator() {
        return animator;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public StatusEvent getStatus() {
        return status;
    }

    public Space getSpace() {
        return space;
    }

    public List<Customer> getSubscribers() {
        return subscribers;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    public int hashCode() {
        return Objects.hash(id, animator, schedule, status, space);
    }
}
