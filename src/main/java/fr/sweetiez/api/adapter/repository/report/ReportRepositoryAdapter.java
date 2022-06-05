package fr.sweetiez.api.adapter.repository.report;

import fr.sweetiez.api.adapter.shared.ReportMapper;
import fr.sweetiez.api.core.reports.models.Report;
import fr.sweetiez.api.core.reports.ports.ReportRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Report> findById(UUID reportId) {
        return reportRepository.findById(reportId).map(reportMapper::toDto);
    }

    public void delete(UUID id) {
        reportRepository.deleteById(id);
    }
}
