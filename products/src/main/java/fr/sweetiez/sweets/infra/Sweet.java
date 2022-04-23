package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.Status;
import fr.sweetiez.sweets.domain.SweetType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Sweet {

    @Id
    @GeneratedValue
    private final Long dbId;

    @Column(unique = true)
    private final UUID id;

    private final String name;

    private final String description;

    private final Priority priority;

    private final Status state;

    private final SweetType type;

    private final LocalDateTime created;

    private final UUID creator;

    private final LocalDateTime updated;

    @Column(name = "update_author")
    private final UUID updateAuthor;

    public Sweet() {
        this.dbId = null;
        this.id = null;
        this.name = null;
        this.description = null;
        this.priority = null;
        this.state = null;
        this.type = null;
        this.created = null;
        this.creator = null;
        this.updated = null;
        this.updateAuthor = null;
    }

    public Sweet(Long dbId, UUID id, String name, String description, Priority priority, Status state, SweetType type, LocalDateTime created, UUID creator, LocalDateTime updated, UUID updateAuthor) {
        this.dbId = dbId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.state = state;
        this.type = type;
        this.created = created;
        this.creator = creator;
        this.updated = updated;
        this.updateAuthor = updateAuthor;
    }

    public Long getDbId() {
        return dbId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getState() {
        return state;
    }

    public SweetType getType() {
        return type;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UUID getCreator() {
        return creator;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public UUID getUpdateAuthor() {
        return updateAuthor;
    }
}
