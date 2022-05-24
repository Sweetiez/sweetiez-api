package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.infrastructure.repository.reports.ReportEntity;

public class ReportMapper {

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
