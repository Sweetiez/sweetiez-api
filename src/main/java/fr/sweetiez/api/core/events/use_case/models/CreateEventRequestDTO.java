package fr.sweetiez.api.core.events.use_case.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateEventRequestDTO {
    private final UUID animatorId;
    private final String title;
    private final LocalDateTime startDateTime;
    private final Duration duration;
    private final UUID spaceId;

    public CreateEventRequestDTO(UUID animatorId, String title,
                                 LocalDateTime startDateTime,
                                 Duration duration, UUID spaceId) {
        this.animatorId = animatorId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.spaceId = spaceId;
    }

    public UUID getAnimatorId() {
        return animatorId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public UUID getSpaceId() {
        return spaceId;
    }

    public String toString() {
        return "CreateEventRequestDTO{" +
                "animatorId=" + animatorId +
                ", title='" + title + '\'' +
                ", startDateTime=" + startDateTime +
                ", duration=" + duration +
                ", spaceId=" + spaceId +
                '}';
    }
}
