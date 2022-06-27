package fr.sweetiez.api.core.orders.models.responses;

import java.math.BigDecimal;

public record ProductOrderedResponse(String name,
                                     Integer quantity,
                                     Integer unitPerPackage,
                                     BigDecimal unitPrice,
                                     BigDecimal total
) {}
