package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.core.reports.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.reports.models.responses.AdminReportResponse;
import fr.sweetiez.api.core.reports.services.ReportAlreadyCreatedByUserException;
import fr.sweetiez.api.core.reports.services.ReportService;
import org.springframework.http.HttpStatus;
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
        catch (ReportAlreadyCreatedByUserException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Collection<AdminReportResponse>> retrieveAll() {
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

    public ResponseEntity<Object> deleteSpamReport(String id) {
        try {
            service.deleteReport(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
