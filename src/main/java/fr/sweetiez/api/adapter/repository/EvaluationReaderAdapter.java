package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.EvaluationMapper;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class EvaluationReaderAdapter implements EvaluationReader {

    private final EvaluationRepository repository;
    private final EvaluationMapper evaluationMapper;

    public EvaluationReaderAdapter(EvaluationRepository repository, EvaluationMapper evaluationMapper) {
        this.repository = repository;
        this.evaluationMapper = evaluationMapper;
    }

    public Collection<Evaluation> retrieveAllBySubject(UUID subject) {
        return repository.findAllBySubject(subject)
                .stream()
                .map(evaluationMapper::toDto)
                .collect(Collectors.toSet());
    }
}
