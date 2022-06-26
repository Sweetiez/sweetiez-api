package fr.sweetiez.api.infrastructure.repository.events.animator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ReservedAnimatorEntity implements Serializable {

    @Id
    private final UUID id;

    private final LocalDateTime start;

    private final Duration duration;

    public ReservedAnimatorEntity() {
        this.id = null;
        this.start = null;
        this.duration = null;
    }

    public ReservedAnimatorEntity(UUID id, LocalDateTime start, Duration duration) {
        this.id = id;
        this.start = start;
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedAnimatorEntity that = (ReservedAnimatorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(start, that.start) && Objects.equals(duration, that.duration);
    }

    public int hashCode() {
        return Objects.hash(id, start, duration);
    }

    public String toString() {
        return "ReservedAnimatorEntity{" +
                "id=" + id +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }
}
