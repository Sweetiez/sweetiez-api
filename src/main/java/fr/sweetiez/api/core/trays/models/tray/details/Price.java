package fr.sweetiez.api.core.trays.models.tray.details;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public boolean isValid() {
        return value != null && value.doubleValue() > 0.;
    }
}
