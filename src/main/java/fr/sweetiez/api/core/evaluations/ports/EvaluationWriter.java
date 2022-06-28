package fr.sweetiez.api.core.evaluations.ports;

import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;

public interface EvaluationWriter {
    Evaluation save(Evaluation evaluation);
    void delete(EvaluationId id);
}
