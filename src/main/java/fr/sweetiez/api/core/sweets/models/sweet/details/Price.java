package fr.sweetiez.api.core.sweets.models.sweet.details;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public boolean isValid() {
        return value != null && value.doubleValue() > 0.;
    }
}
