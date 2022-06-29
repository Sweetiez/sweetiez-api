package fr.sweetiez.api.core.events.events.face_to_face_event;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.events.EventID;
import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FaceToFaceEvent {
    private final EventID id;
    private final String title;
    private final String description;
    private final Animator animator;
    private final Schedule schedule;
    private final StatusEvent status;
    private final Space space;
    private final List<Customer> subscribers;

    public FaceToFaceEvent(String title, String description, Animator animator, Space space, LocalDateTime startDateTime, Duration duration) {
        Schedule eventSchedule = new Schedule(startDateTime, duration);

        animator.book(eventSchedule);
        space.book(eventSchedule);

        this.id = new EventID(UUID.randomUUID());
        this.title = title;
        this.description = description;
        this.status = StatusEvent.CREATED;
        this.animator = animator;
        this.schedule = eventSchedule;
        this.space = space;
        this.subscribers = List.of();
    }

    public FaceToFaceEvent(FaceToFaceEventDto dto) {
        this.id = new EventID(dto.id());
        this.title = dto.title();
        this.description = dto.description();
        this.animator = dto.animator();
        this.schedule = dto.schedule();
        this.status = dto.status();
        this.space = dto.space();
        this.subscribers = dto.subscribers();
    }

    private FaceToFaceEvent(FaceToFaceEvent event, StatusEvent status) {
        this.id = event.id;
        this.title = event.title;
        this.description = event.description;
        this.status = status;
        this.animator = event.animator;
        this.schedule = event.schedule;
        this.space = event.space;
        this.subscribers = event.subscribers;
    }

    public FaceToFaceEvent(FaceToFaceEvent event, LocalDateTime newStart, Duration newDuration) {
        Schedule eventSchedule = new Schedule(newStart, newDuration);

        event.animator.reschedule(event.schedule, eventSchedule);
        event.space.reschedule(event.schedule, eventSchedule);

        this.id = event.id;
        this.title = event.title;
        this.description = event.description;
        this.status = event.status;
        this.animator = event.animator;
        this.schedule = eventSchedule;
        this.space = event.space;
        this.subscribers = event.subscribers;
    }

    public static FaceToFaceEvent publish(FaceToFaceEvent event) {
        return new FaceToFaceEvent(event, StatusEvent.PUBLISHED);
    }

    public static FaceToFaceEvent cancel(FaceToFaceEvent event) {
        return new FaceToFaceEvent(event, StatusEvent.CANCELLED);
    }

    public static FaceToFaceEvent reschedule(FaceToFaceEvent event, LocalDateTime start, Duration duration) {
        return new FaceToFaceEvent(event, start, duration);
    }

    public EventID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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
        FaceToFaceEvent event = (FaceToFaceEvent) o;
        return Objects.equals(id, event.id);
    }

    public int hashCode() {
        return Objects.hash(id, animator, schedule, status, space);
    }
}
