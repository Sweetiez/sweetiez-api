package fr.sweetiez.api.core.products.models.common;

import java.math.BigDecimal;

public record Price(BigDecimal unitPrice, int unitPerPackage) {
    public Price(BigDecimal unitPrice) {
        this(unitPrice, 1);
    }

    public boolean isValid() {
        return unitPrice != null && unitPrice.doubleValue() > 0. && unitPerPackage > 0;
    }

    public BigDecimal packaged() {
        return unitPrice.multiply(new BigDecimal(unitPerPackage));
    }
}
