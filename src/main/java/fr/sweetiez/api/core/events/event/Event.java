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
    private final Animator animator;
    private final Schedule schedule;
    private final StatusEvent status;
    private final Space space;
    private final List<Customer> subscribers;


    public Event(Animator animator, Space space, LocalDateTime startDateTime, Duration duration) {
        Schedule eventSchedule = new Schedule(startDateTime, duration);

        animator.book(eventSchedule);
        space.book(eventSchedule);

        this.id = new EventID(UUID.randomUUID());
        this.status = StatusEvent.CREATED;
        this.animator = animator;
        this.schedule = eventSchedule;
        this.space = space;
        this.subscribers = List.of();
    }

    public EventID getId() {
        return id;
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
