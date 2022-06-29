package fr.sweetiez.api.core.dashboard.models.dashboard;

import java.time.LocalDate;

public record ChartElement(LocalDate date, double value) {
}
