package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.ReportMapper;
import fr.sweetiez.api.core.evaluations.models.Report;
import fr.sweetiez.api.core.reports.ports.ReportRepository;

import java.util.Collection;

public class ReportRepositoryAdapter implements ReportRepository {

    private final fr.sweetiez.api.infrastructure.repository.reports.ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportRepositoryAdapter(fr.sweetiez.api.infrastructure.repository.reports.ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public Report save(Report report) {
        var entity = reportRepository.save(reportMapper.toEntity(report));
        return reportMapper.toDto(entity);
    }

    public Collection<Report> retrieveAll() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toDto)
                .toList();
    }
}
