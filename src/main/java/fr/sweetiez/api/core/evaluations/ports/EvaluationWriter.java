package fr.sweetiez.api.core.evaluations.ports;

import fr.sweetiez.api.core.evaluations.models.Report;
import fr.sweetiez.api.core.evaluations.models.Evaluation;

public interface EvaluationWriter {
    Evaluation save(Evaluation evaluation);
    Report save(Report evaluation);
}
