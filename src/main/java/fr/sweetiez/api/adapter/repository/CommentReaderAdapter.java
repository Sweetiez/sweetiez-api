package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.CommentMapper;
import fr.sweetiez.api.core.comments.models.Comment;
import fr.sweetiez.api.core.comments.ports.CommentReader;
import fr.sweetiez.api.infrastructure.repository.CommentRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentReaderAdapter implements CommentReader {

    private final CommentRepository repository;
    private final CommentMapper commentMapper;

    public CommentReaderAdapter(CommentRepository repository, CommentMapper commentMapper) {
        this.repository = repository;
        this.commentMapper = commentMapper;
    }

    public Collection<Comment> findAllBySubjectId(String id) {
        return repository.findAllBySubject(UUID.fromString(id))
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toSet());
    }
}
