package fr.sweetiez.api.core.reports.ports;

import fr.sweetiez.api.core.evaluations.models.Report;

import java.util.Collection;

public interface ReportRepository {
    Report save(Report report);
    Collection<Report> retrieveAll();
}
