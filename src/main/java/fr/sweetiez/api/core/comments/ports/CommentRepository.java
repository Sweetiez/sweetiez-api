package fr.sweetiez.api.core.comments.ports;

import fr.sweetiez.api.core.comments.models.Comment;

import java.util.Collection;

public interface CommentRepository {
    Collection<Comment> findAllBySubjectId(String id);
}
