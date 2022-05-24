package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.EvaluationMapper;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.Report;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.ReportRepository;

public class EvaluationWriterAdapter implements EvaluationWriter {

    private final EvaluationRepository evaluationRepository;
    private final ReportRepository reportRepository;
    private final EvaluationMapper evaluationMapper;

    public EvaluationWriterAdapter(EvaluationRepository evaluationRepository, ReportRepository reportRepository, EvaluationMapper evaluationMapper) {
        this.evaluationRepository = evaluationRepository;
        this.reportRepository = reportRepository;
        this.evaluationMapper = evaluationMapper;
    }

    public Evaluation save(Evaluation evaluation) {
        var entity = evaluationRepository.save(evaluationMapper.toEntity(evaluation));
        return evaluationMapper.toDto(entity);
    }

    public Report save(Report report) {
        var entity = reportRepository.save(evaluationMapper.toEntity(report));
        return evaluationMapper.toDto(entity);
    }
}
