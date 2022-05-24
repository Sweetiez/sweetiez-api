package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.adapter.delivery.ReportEndPoints;
import fr.sweetiez.api.core.evaluations.models.ReportEvaluationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class SpringReportController {

    private final ReportEndPoints reportEndPoints;

    public SpringReportController(ReportEndPoints reportEndPoints) {
        this.reportEndPoints = reportEndPoints;
    }

    @PostMapping
    public ResponseEntity<?> report(@RequestBody ReportEvaluationRequest request) {
        return reportEndPoints.create(request);
    }
}
