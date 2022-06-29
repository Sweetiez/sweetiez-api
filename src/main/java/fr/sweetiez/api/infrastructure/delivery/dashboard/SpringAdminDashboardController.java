package fr.sweetiez.api.infrastructure.delivery.dashboard;

import fr.sweetiez.api.adapter.delivery.dashboard.DashboardEndPoints;
import fr.sweetiez.api.core.dashboard.models.dashboard.Dashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
public class SpringAdminDashboardController {

    private final DashboardEndPoints dashboardEndPoints;

    public SpringAdminDashboardController(DashboardEndPoints dashboardEndPoints) {
        this.dashboardEndPoints = dashboardEndPoints;
    }

    @GetMapping
    public Dashboard getDashboard() {
        return dashboardEndPoints.getDashboard();
    }
}
