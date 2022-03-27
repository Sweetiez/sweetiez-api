package fr.sweetiez.sweets.model;

import java.util.UUID;

public class SweetID {
    private final UUID id;

    public SweetID(UUID id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SweetID sweetID = (SweetID) o;

        return id.equals(sweetID.id);
    }

    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "SweetID{" +
                "id=" + id +
                '}';
    }
}
