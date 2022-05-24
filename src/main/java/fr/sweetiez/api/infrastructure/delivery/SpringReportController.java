package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.ReportEndPoints;
import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.core.reports.models.ReportEvaluationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class SpringReportController {

    private final ReportEndPoints reportEndPoints;

    public SpringReportController(ReportEndPoints reportEndPoints) {
        this.reportEndPoints = reportEndPoints;
    }

    @PostMapping("/reports")
    public ResponseEntity<Object> report(@RequestBody ReportEvaluationRequest request) {
        return reportEndPoints.create(request);
    }

    @GetMapping("/admin/reports")
    public ResponseEntity<Collection<Report>> retrieveAll() {
        return reportEndPoints.retrieveAll();
    }

    @DeleteMapping("/admin/reports/{id}")
    public ResponseEntity<Object> deleteReportedEvaluation(@PathVariable("id") String id) {
        return reportEndPoints.deleteReportedEvaluation(id);
    }
}
