package fr.sweetiez.api.adapter.delivery.dashboard;

import fr.sweetiez.api.core.dashboard.models.dashboard.Dashboard;
import fr.sweetiez.api.core.dashboard.services.DashboardService;

public class DashboardEndPoints {
    private final DashboardService dashboardService;


    public DashboardEndPoints(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    public Dashboard getDashboard() {
        return dashboardService.getDashboard();
    }
}
