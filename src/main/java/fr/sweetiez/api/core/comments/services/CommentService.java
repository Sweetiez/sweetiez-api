package fr.sweetiez.api.core.comments.services;

import fr.sweetiez.api.core.comments.models.Comment;
import fr.sweetiez.api.core.comments.ports.CommentReader;

import java.util.Collection;

public class CommentService {

    private final CommentReader repository;

    public CommentService(CommentReader repository) {
        this.repository = repository;
    }

    public Collection<Comment> retrieveCommentsBySubject(String id) {
        return repository.findAllBySubjectId(id);
    }
}
