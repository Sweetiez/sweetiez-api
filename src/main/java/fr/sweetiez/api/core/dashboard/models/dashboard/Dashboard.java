package fr.sweetiez.api.core.dashboard.models.dashboard;

import java.util.List;

public record Dashboard(List<ChartElement> salesChart,
                        List<SimpleOrder> tenFirstOrders,
                        Informations informations,
                        List<TopSale> topSales) {
}
