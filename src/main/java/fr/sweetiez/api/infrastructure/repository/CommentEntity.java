package fr.sweetiez.api.infrastructure.repository;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(columnDefinition = "text")
    private final String content;

    private final UUID author;

    private final UUID subject;

    public CommentEntity() {
        id = null;
        content = null;
        author = null;
        subject = null;
    }

    public CommentEntity(Long id, String content, UUID author, UUID sweetId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.subject = sweetId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UUID getAuthor() {
        return author;
    }

    public UUID getSubject() {
        return subject;
    }
}
