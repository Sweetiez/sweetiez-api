package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.Status;
import fr.sweetiez.sweets.domain.SweetType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="SWEET")
public class SweetDatabaseModel {

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

    public SweetDatabaseModel() {
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

    public static class SweetBuilder
    {

        private Long dbId;
        private UUID id;
        private String name;
        private String description;
        private Priority priority;
        private Status state;
        private SweetType type;
        private LocalDateTime created;
        private UUID creator;
        private LocalDateTime updated;
        private UUID updateAuthor;

        public SweetDatabaseModel build() {
            if(created == null) {
                created = LocalDateTime.now();
            }

            return new SweetDatabaseModel(
                    dbId,
                    id,
                    name,
                    description,
                    priority,
                    state,
                    type,
                    created,
                    creator,
                    updated,
                    updateAuthor
            );
        }

        public SweetBuilder dbId(Long dbId) {
            this.dbId = dbId;
            return this;
        }

        public SweetBuilder id(UUID id) {
            this.id = id;
            return this;
        }
        public SweetBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SweetBuilder description(String description) {
            this.description = description;
            return this;
        }
        public SweetBuilder priority(Priority priority) {
            this.priority = priority;
            return this;
        }
        public SweetBuilder state(Status state) {
            this.state = state;
            return this;
        }
        public SweetBuilder type(SweetType type) {
            this.type = type;
            return this;
        }
        public SweetBuilder created(LocalDateTime created) {
            this.created = created;
            return this;
        }
        public SweetBuilder creator(UUID creator) {
            this.creator = creator;
            return this;
        }
        public SweetBuilder updated(LocalDateTime updated) {
            this.updated = updated;
            return this;
        }

        public SweetBuilder updateAuthor(UUID updateAuthor) {
            this.updateAuthor = updateAuthor;
            return this;
        }
    }

    private SweetDatabaseModel(Long dbId, UUID id, String name, String description, Priority priority, Status state, SweetType type, LocalDateTime created, UUID creator, LocalDateTime updated, UUID updateAuthor) {
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
