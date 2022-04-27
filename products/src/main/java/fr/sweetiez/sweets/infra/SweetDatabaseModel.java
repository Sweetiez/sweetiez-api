package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.Status;
import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.SweetType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="sweet")
public class SweetDatabaseModel {

    @Id
    @GeneratedValue
    private final Long dbId;

    @Column(unique = true)
    private final UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private Priority priority;

    private Status state;

    private SweetType type;

    private final LocalDateTime created;

    private final UUID creator;

    private LocalDateTime updated;

    @Column(name = "update_author")
    private UUID updateAuthor;

    public SweetDatabaseModel() {
        this.dbId = null;
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.priority = null;
        this.state = null;
        this.type = null;
        this.created = null;
        this.creator = null;
        this.updated = null;
        this.updateAuthor = null;
    }

    public Sweet toSweet() {
        return new Sweet.Builder()
                .id(id)
                .description(description)
                .name(name)
                .status(state)
                .priority(priority)
                .type(type)
                .price(price)
                .build();
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

    private SweetDatabaseModel(SweetBuilder builder) {
        this.dbId = builder.dbId;
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.priority = builder.priority;
        this.state = builder.state;
        this.type = builder.type;
        this.created = builder.created;
        this.creator = builder.creator;
        this.updated = builder.updated;
        this.updateAuthor = builder.updateAuthor;
    }

    public static class SweetBuilder {

        private Long dbId;
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
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

            return new SweetDatabaseModel(this);
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

        public SweetBuilder price(BigDecimal price) {
            this.price = price;
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
}
