package fr.sweetiez.api.infrastructure.repository.reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {
    void deleteAllByEvaluationId(UUID evaluationId);
}
