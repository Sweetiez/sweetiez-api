package fr.sweetiez.api.infrastructure.repository.evaluations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, UUID> {
    Collection<EvaluationEntity> findAllBySubject(UUID subject);
}
