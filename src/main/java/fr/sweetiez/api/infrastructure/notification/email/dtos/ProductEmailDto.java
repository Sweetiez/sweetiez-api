package fr.sweetiez.api.infrastructure.notification.email.dtos;

import java.math.BigDecimal;

public record ProductEmailDto(String name,
                              Integer quantity,
                              Integer unitPerPackage,
                              BigDecimal unitPrice,
                              BigDecimal total) {
}
