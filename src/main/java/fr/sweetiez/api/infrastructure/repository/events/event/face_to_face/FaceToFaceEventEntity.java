package fr.sweetiez.api.infrastructure.repository.events.event.face_to_face;

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
@Table(name = "face_to_face_event")
public class FaceToFaceEventEntity {

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

    private final UUID space;


    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CustomerEntity> subscribers;

    public FaceToFaceEventEntity() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.animator = null;
        this.start = null;
        this.endDate = null;
        this.status = null;
        this.space = null;
        this.subscribers = null;
    }

    public FaceToFaceEventEntity(UUID id, String title, String description, UUID animator, LocalDateTime start,
                                 LocalDateTime end, StatusEvent status, UUID space, List<CustomerEntity> subscribers)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.animator = animator;
        this.start = start;
        this.endDate = end;
        this.status = status;
        this.space = space;
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

    public UUID getSpace() {
        return space;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaceToFaceEventEntity that = (FaceToFaceEventEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(animator, that.animator) && Objects.equals(start, that.start) && Objects.equals(endDate, that.endDate) && status == that.status && Objects.equals(space, that.space) && Objects.equals(subscribers, that.subscribers);
    }

    public int hashCode() {
        return Objects.hash(id, animator, start, endDate, status, space, subscribers);
    }

    public String toString() {
        return "FaceToFaceEventEntity{" +
                "id=" + id +
                ", animator=" + animator +
                ", start=" + start +
                ", end=" + endDate +
                ", status=" + status +
                ", space=" + space +
                ", subscribers=" + subscribers +
                '}';
    }
}
