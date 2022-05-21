package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationEntity;

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
}
