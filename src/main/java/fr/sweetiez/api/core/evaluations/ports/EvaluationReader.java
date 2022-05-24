package fr.sweetiez.api.core.evaluations.ports;

import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;

import java.util.Collection;
import java.util.UUID;

public interface EvaluationReader {
    Collection<Evaluation> retrieveAllBySubject(UUID subject);
    boolean exists(EvaluationId evaluationId);
}
