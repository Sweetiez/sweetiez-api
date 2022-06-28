package fr.sweetiez.api.core.events.space;

import java.util.Objects;
import java.util.UUID;

public class SpaceID {
    private final UUID spaceID;

    public SpaceID(UUID spaceID) {
        this.spaceID = spaceID;
    }

    public UUID getSpaceID() {
        return spaceID;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceID spaceID1 = (SpaceID) o;
        return Objects.equals(spaceID, spaceID1.spaceID);
    }

    public int hashCode() {
        return Objects.hash(spaceID);
    }
}

