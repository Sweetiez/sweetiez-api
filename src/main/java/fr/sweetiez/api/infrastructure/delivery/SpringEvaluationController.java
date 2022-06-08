package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.EvaluationEndPoints;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable("id") String id) {
        return evaluationEndPoints.getEvaluation(id);
    }
}
