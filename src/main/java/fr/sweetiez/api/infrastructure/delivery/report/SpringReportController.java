package fr.sweetiez.api.infrastructure.delivery.report;

import fr.sweetiez.api.adapter.delivery.report.ReportEndPoints;
import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.core.reports.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.reports.models.responses.AdminReportResponse;
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
    public ResponseEntity<Collection<AdminReportResponse>> retrieveAll() {
        return reportEndPoints.retrieveAll();
    }

    @DeleteMapping("/admin/reports/{id}")
    public ResponseEntity<Object> deleteReportedEvaluation(@PathVariable("id") String id) {
        return reportEndPoints.deleteReportedEvaluation(id);
    }

    @DeleteMapping("/admin/reports/{id}/spam")
    public ResponseEntity<Object> deleteSpamReport(@PathVariable("id") String id) {
        return reportEndPoints.deleteSpamReport(id);
    }
}
