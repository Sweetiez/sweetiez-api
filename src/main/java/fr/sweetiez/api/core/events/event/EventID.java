package fr.sweetiez.api.core.events.event;

import java.util.Objects;
import java.util.UUID;

public class EventID {
    private final UUID eventId;

    public EventID(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventID eventID = (EventID) o;
        return Objects.equals(eventId, eventID.eventId);
    }

    public int hashCode() {
        return Objects.hash(eventId);
    }
}

