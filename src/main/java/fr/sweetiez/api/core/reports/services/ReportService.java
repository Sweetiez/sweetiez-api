package fr.sweetiez.api.core.reports.services;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.core.evaluations.models.Report;
import fr.sweetiez.api.core.evaluations.models.ReportEvaluationRequest;
import fr.sweetiez.api.core.evaluations.services.EvaluationDoesNotExistException;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.reports.ports.ReportRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportService {

    private final CustomerService customerService;
    private final EvaluationService evaluationService;
    private final ReportRepository reportRepository;

    public ReportService(CustomerService customerService, EvaluationService evaluationService, ReportRepository reportRepository) {
        this.customerService = customerService;
        this.evaluationService = evaluationService;
        this.reportRepository = reportRepository;
    }

    public Report create(ReportEvaluationRequest request) {
        if (!customerService.exists(new CustomerId(request.reporterId()))) {
            throw new CustomerDoesNotExistException();
        }

        if (!evaluationService.exists(new EvaluationId(request.evaluationId()))) {
            throw new EvaluationDoesNotExistException();
        }

        var reportedEvaluation = new Report(
                null,
                UUID.fromString(request.reporterId()),
                UUID.fromString(request.evaluationId()),
                request.reason(),
                LocalDateTime.now());

        return reportRepository.save(reportedEvaluation);
    }
}
