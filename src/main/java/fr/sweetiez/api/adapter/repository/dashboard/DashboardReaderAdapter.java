package fr.sweetiez.api.adapter.repository.dashboard;

import fr.sweetiez.api.adapter.shared.OrderMapper;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.adapter.shared.TrayMapper;
import fr.sweetiez.api.core.dashboard.models.dashboard.*;
import fr.sweetiez.api.core.dashboard.ports.DashboardReader;
import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import fr.sweetiez.api.core.orders.models.orders.Orders;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderEntity;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetRepository;
import fr.sweetiez.api.infrastructure.repository.products.trays.TrayRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import net.bytebuddy.build.Plugin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardReaderAdapter implements DashboardReader {
    /**
     * Repositories
     */
    private final SweetRepository sweetRepository;
    private final TrayRepository trayRepository;
    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final RecipeRepository recipeRepository;
    private final AccountRepository accountRepository;

    /**
     * Mappers
     */
    private final OrderMapper orderMapper;
    private final SweetMapper sweetMapper;
    private final TrayMapper trayMapper;

    public DashboardReaderAdapter(SweetRepository sweetRepository,
                                  TrayRepository trayRepository,
                                  OrderRepository orderRepository,
                                  RecipeRepository recipeRepository, AccountRepository accountRepository,
                                  OrderMapper orderMapper,
                                  SweetMapper sweetMapper,
                                  TrayMapper trayMapper,
                                  OrderDetailRepository orderDetailRepository) {
        this.sweetRepository = sweetRepository;
        this.trayRepository = trayRepository;
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.orderMapper = orderMapper;
        this.sweetMapper = sweetMapper;
        this.trayMapper = trayMapper;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Dashboard getDashboard() {
        var clientsNumber = ((int) this.accountRepository.count());
        var publishedSweets = this.sweetRepository.countByState(State.PUBLISHED);
        var publishedTrays = this.trayRepository.countByState(State.PUBLISHED);
        var publishedRecipes = this.recipeRepository.countByState(fr.sweetiez.api.core.recipes.models.recipes.details.State.PUBLISHED);
        var date = LocalDate.now();
        var ordersFromLastMonth = this.orderRepository.findAllByCreatedAtAfter(date.minusDays(date.getDayOfMonth() -1));


        var monthSales = computeMonthSales(ordersFromLastMonth);
        var chartCurrentMonth = new ChartElement(date.minusDays(date.getDayOfMonth() -1), monthSales);

        var topSales = getTopSales(ordersFromLastMonth);

        var orders = getLastOrders();

        var chart = getChart(chartCurrentMonth);

//        System.out.println(clientsNumber);
//        System.out.println(publishedSweets);
//        System.out.println(publishedTrays);
//        System.out.println(publishedRecipes);
//        System.out.println(monthSales);
//        System.out.println(orders.size());

        return new Dashboard(
                chart,
                orders,
                new Informations(clientsNumber, publishedRecipes, publishedSweets, publishedTrays, monthSales),
                topSales
        );
    }

    private List<ChartElement> getChart(ChartElement firstElement) {
        var chart = new ArrayList<ChartElement>();
        chart.add(firstElement);

        // Get dates from 6 months ago
        var date = LocalDate.now();
        var firstOfCurrentMonth = date.minusDays(date.getDayOfMonth() -1);
        var oneMonthsAgo = firstOfCurrentMonth.minusMonths(1);
        var twoMonthsAgo = firstOfCurrentMonth.minusMonths(2);
        var threeMonthsAgo = firstOfCurrentMonth.minusMonths(3);
        var fourMonthsAgo = firstOfCurrentMonth.minusMonths(4);
        var fiveMonthsAgo = firstOfCurrentMonth.minusMonths(5);
        var sixMonthsAgo = firstOfCurrentMonth.minusMonths(6);


        var salesFromCurrentMonth = this.orderRepository.findAllByCreatedAtAfter(firstOfCurrentMonth);
        var salesFromLastMonth = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(oneMonthsAgo, firstOfCurrentMonth));
        var salesFromTwoMonthAgo = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(twoMonthsAgo, oneMonthsAgo));
        var salesFromThreeMonthAgo = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(threeMonthsAgo, twoMonthsAgo));
        var salesFromFourMonthAgo = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(fourMonthsAgo, threeMonthsAgo));
        var salesFromFiveMonthAgo = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(fiveMonthsAgo, fourMonthsAgo));
        var salesFromSixMonthAgo = computeMonthSales(this.orderRepository.findAllByCreatedAtAfterAndCreatedAtBefore(sixMonthsAgo, fiveMonthsAgo));

        chart.add(new ChartElement(oneMonthsAgo, salesFromLastMonth));
        chart.add(new ChartElement(twoMonthsAgo, salesFromTwoMonthAgo));
        chart.add(new ChartElement(threeMonthsAgo, salesFromThreeMonthAgo));
        chart.add(new ChartElement(fourMonthsAgo, salesFromFourMonthAgo));
        chart.add(new ChartElement(fiveMonthsAgo, salesFromFiveMonthAgo));
        chart.add(new ChartElement(sixMonthsAgo, salesFromSixMonthAgo));

        return chart;
    }

    private double computeMonthSales(List<OrderEntity> ordersFromLastMonth) {
        return ordersFromLastMonth
                .stream()
                .mapToDouble(OrderEntity::getTotalPrice)
                .sum();
    }

    private List<SimpleOrder> getLastOrders() {
        return this.orderRepository.findAllByStatusOrderByPickupDateAsc(OrderStatus.PAID)
                .stream()
                .map(orderMapper::toSimpleDto)
                .limit(10)
                .map(SimpleOrder::new)
                .toList();
    }

    private List<TopSale> getTopSales(List<OrderEntity> ordersFromLastMonth) {
        var topSalesMap = new HashMap<String, Integer>();
        ordersFromLastMonth.forEach(entity -> {
            // Retrieve ordered products
            var orderDetails = this.orderDetailRepository.findAllByOrderId(entity.getId());

            orderDetails.forEach(orderDetail -> {
                if (topSalesMap.containsKey(orderDetail.getName())) {
                    topSalesMap.put(orderDetail.getName(), topSalesMap.get(orderDetail.getName()) + 1);
                } else {
                    topSalesMap.put(orderDetail.getName(), 1);
                }
            });
        });

        var topSales = new ArrayList<TopSale>();
        topSalesMap.forEach((name, quantity) -> {
            topSales.add(new TopSale(name, quantity));
        });

        return topSales;
    }
}
