package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.EvaluationEndPoints;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.ReportEvaluationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluations")
public class SpringEvaluationController {

    private final EvaluationEndPoints evaluationEndPoints;

    public SpringEvaluationController(EvaluationEndPoints evaluationEndPoints) {
        this.evaluationEndPoints = evaluationEndPoints;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateEvaluationRequest request) {
        return evaluationEndPoints.create(request);
    }

    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody ReportEvaluationRequest request) {
        return evaluationEndPoints.report(request);
    }
}
