package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.core.reports.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.reports.services.ReportService;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;

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
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Collection<Report>> retrieveAll() {
        return ResponseEntity.ok(service.retrieveAll());
    }

    public ResponseEntity<Object> deleteReportedEvaluation(String id) {
        try {
            service.deleteReportedEvaluationWithReport(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}