package fr.sweetiez.api.adapter.repository.evaluations;

import fr.sweetiez.api.adapter.shared.EvaluationMapper;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;

import java.util.UUID;

public class EvaluationWriterAdapter implements EvaluationWriter {

    private final EvaluationRepository evaluationRepository;
    private final EvaluationMapper evaluationMapper;

    public EvaluationWriterAdapter(EvaluationRepository evaluationRepository, EvaluationMapper evaluationMapper) {
        this.evaluationRepository = evaluationRepository;
        this.evaluationMapper = evaluationMapper;
    }

    public Evaluation save(Evaluation evaluation) {
        var entity = evaluationRepository.save(evaluationMapper.toEntity(evaluation));
        return evaluationMapper.toDto(entity);
    }

    public void delete(EvaluationId id) {
        evaluationRepository.deleteById(UUID.fromString(id.value()));
    }


}
