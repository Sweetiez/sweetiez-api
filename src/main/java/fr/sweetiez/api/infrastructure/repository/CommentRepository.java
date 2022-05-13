package fr.sweetiez.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Collection<CommentEntity> findAllBySubject(UUID subject);
}
