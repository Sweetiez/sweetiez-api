package fr.sweetiez.api.core.reports.ports;

import fr.sweetiez.api.core.evaluations.models.Report;

public interface ReportRepository {
    Report save(Report report);
}
