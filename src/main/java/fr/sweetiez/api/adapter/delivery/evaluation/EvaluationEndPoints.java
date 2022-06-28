package fr.sweetiez.api.adapter.delivery.evaluation;

import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.orders.models.responses.DetailedOrderResponse;
import fr.sweetiez.api.core.orders.services.exceptions.OrderNotFoundException;
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

    public ResponseEntity<Evaluation> getEvaluation(String id) {
        try {
            return ResponseEntity.ok().body(evaluationService.getById(id));
        } catch (EvaluationDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
