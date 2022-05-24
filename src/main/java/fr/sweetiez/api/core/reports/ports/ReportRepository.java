package fr.sweetiez.api.core.reports.ports;

import fr.sweetiez.api.core.reports.models.Report;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository {
    Report save(Report report);
    Collection<Report> retrieveAll();
    Optional<Report> findById(UUID reportId);
    void delete(UUID id);
}
