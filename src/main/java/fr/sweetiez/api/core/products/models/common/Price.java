package fr.sweetiez.api.core.products.models.common;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public boolean isValid() {
        return value != null && value.doubleValue() > 0.;
    }
}
