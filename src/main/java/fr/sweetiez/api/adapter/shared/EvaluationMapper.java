package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.evaluations.models.Report;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationEntity;
import fr.sweetiez.api.infrastructure.repository.evaluations.ReportEntity;

import java.util.UUID;

public class EvaluationMapper {

    public EvaluationEntity toEntity(Evaluation evaluation) {
        var id = evaluation.id() != null ? UUID.fromString(evaluation.id().value()) : null;
        return new EvaluationEntity(
                id,
                evaluation.comment(),
                evaluation.voter(),
                evaluation.subject(),
                evaluation.mark().intValue()
        );
    }

    public Evaluation toDto(EvaluationEntity entity) {
        return new Evaluation(
                new EvaluationId(entity.getId().toString()),
                entity.getComment(),
                entity.getAuthor(),
                entity.getSubject(),
                entity.getMark().doubleValue()
        );
    }

    public ReportEntity toEntity(Report report) {
        return new ReportEntity(
                report.id(),
                report.reporterId(),
                report.evaluationId(),
                report.reason(),
                report.creationDate()
        );
    }

    public Report toDto(ReportEntity entity) {
        return new Report(
                entity.getId(),
                entity.getReporterId(),
                entity.getEvaluationId(),
                entity.getReason(),
                entity.getCreated()
        );
    }
}
