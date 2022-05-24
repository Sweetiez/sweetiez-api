package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.reports.services.ReportService;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ReportEndPoints {

    private final ReportService service;

    public ReportEndPoints(ReportService service) {
        this.service = service;
    }

    public ResponseEntity<Object> create(ReportEvaluationRequest request) {
        try {
            var reportedEvaluation = service.create(request);
            return ResponseEntity
                    .created(URI.create("/reports/" + reportedEvaluation.id()))
                    .build();
        }
        catch (CustomerDoesNotExistException | EvaluationDoesNotExistException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
