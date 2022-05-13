package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.comments.models.Comment;
import fr.sweetiez.api.infrastructure.repository.CommentEntity;

public class CommentMapper {

    public CommentEntity toEntity(Comment comment) {
        return new CommentEntity(
                null,
                comment.content(),
                comment.author(),
                comment.subject()
        );
    }

    public Comment toDto(CommentEntity entity) {
        return new Comment(entity.getContent(), entity.getAuthor(), entity.getSubject());
    }
}
