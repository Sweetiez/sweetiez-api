package fr.sweetiez.api.core.dashboard.services;

import fr.sweetiez.api.core.dashboard.models.dashboard.Dashboard;
import fr.sweetiez.api.core.dashboard.ports.DashboardReader;

public class DashboardService {
    private final DashboardReader dashboardReader;

    public DashboardService(DashboardReader dashboardReader) {
        this.dashboardReader = dashboardReader;
    }

    public Dashboard getDashboard() {
        return dashboardReader.getDashboard();
    }
}
