package fr.sweetiez.api.infrastructure.repository.evaluations;

import fr.sweetiez.api.infrastructure.repository.customers.CustomerEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "evaluation")
public class EvaluationEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(columnDefinition = "text")
    private final String comment;

    @OneToOne
    private final CustomerEntity author;

    @Column(nullable = false)
    private final UUID subject;

    @Column(nullable = false)
    private final Integer mark;

    @Column
    private final LocalDateTime date;

    public EvaluationEntity() {
        id = null;
        comment = null;
        author = null;
        subject = null;
        mark = null;
        date = LocalDateTime.now();
    }

    public EvaluationEntity(UUID id, String content, CustomerEntity author, UUID sweetId, Integer mark, LocalDateTime date) {
        this.id = id;
        this.comment = content;
        this.author = author;
        this.subject = sweetId;
        this.mark = mark;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public CustomerEntity getAuthor() {
        return author;
    }

    public UUID getSubject() {
        return subject;
    }

    public Integer getMark() {
        return mark;
    }
    public LocalDateTime getDate() {
        return date;
    }
}
