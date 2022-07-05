package fr.sweetiez.api.infrastructure.repository.events.event.streaming;

import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "streaming_event")
public class StreamingEventEntity {

    @Id
    private final UUID id;

    private final String title;

    private final String description;

    private final UUID animator;

    @Column(columnDefinition = "TIMESTAMP")
    private final LocalDateTime start;

    @Column(columnDefinition = "TIMESTAMP")
    private final LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private final StatusEvent status;

    private final Integer places;


    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CustomerEntity> subscribers;

    public StreamingEventEntity() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.animator = null;
        this.start = null;
        this.endDate = null;
        this.status = null;
        this.places = null;
        this.subscribers = null;
    }

    public StreamingEventEntity(UUID id, String title, String description,
                                UUID animator, LocalDateTime start, LocalDateTime end,
                                StatusEvent status, Integer places, List<CustomerEntity> subscribers)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.animator = animator;
        this.start = start;
        this.endDate = end;
        this.status = status;
        this.places = places;
        this.subscribers = subscribers;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<CustomerEntity> getSubscribers() {
        return subscribers;
    }

    public UUID getAnimator() {
        return animator;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return endDate;
    }

    public StatusEvent getStatus() {
        return status;
    }

    public Integer getPlaces() {
        return places;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreamingEventEntity that = (StreamingEventEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(animator, that.animator) && Objects.equals(start, that.start) && Objects.equals(endDate, that.endDate) && status == that.status && Objects.equals(places, that.places) && Objects.equals(subscribers, that.subscribers);
    }

    public int hashCode() {
        return Objects.hash(id, title, description, animator, start, endDate, status, places, subscribers);
    }

    public String toString() {
        return "StreamingEventEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", animator=" + animator +
                ", start=" + start +
                ", endDate=" + endDate +
                ", status=" + status +
                ", places=" + places +
                ", subscribers=" + subscribers +
                '}';
    }
}
