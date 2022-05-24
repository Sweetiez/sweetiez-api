package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class EvaluationEndPoints {

    private final EvaluationService evaluationService;

    public EvaluationEndPoints(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public ResponseEntity<Object> create(CreateEvaluationRequest request) {
        try {
            var commentCreated = evaluationService.create(request);
            return ResponseEntity
                    .created(URI.create("/evaluations/" + commentCreated.id().value()))
                    .build();
        }
        catch (CustomerDoesNotExistException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> report(ReportEvaluationRequest request) {
        try {
            var reportedEvaluation = evaluationService.report(request);
            return ResponseEntity
                    .created(URI.create("/evaluations/reported/" + reportedEvaluation.id()))
                    .build();
        }
        catch (CustomerDoesNotExistException | EvaluationDoesNotExistException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
