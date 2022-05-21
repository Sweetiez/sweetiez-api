package fr.sweetiez.api.infrastructure.repository.evaluations;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "evaluation")
public class EvaluationEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(columnDefinition = "text")
    private final String comment;

    @Column(nullable = false)
    private final UUID author;

    @Column(nullable = false)
    private final UUID subject;

    @Column(nullable = false)
    private final Integer mark;

    public EvaluationEntity() {
        id = null;
        comment = null;
        author = null;
        subject = null;
        mark = null;
    }

    public EvaluationEntity(UUID id, String content, UUID author, UUID sweetId, Integer mark) {
        this.id = id;
        this.comment = content;
        this.author = author;
        this.subject = sweetId;
        this.mark = mark;
    }

    public UUID getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public UUID getAuthor() {
        return author;
    }

    public UUID getSubject() {
        return subject;
    }

    public Integer getMark() {
        return mark;
    }
}
