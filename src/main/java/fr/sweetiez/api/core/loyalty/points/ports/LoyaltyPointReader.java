package fr.sweetiez.api.core.loyalty.points.ports;

import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.LoyaltyPoints;

public interface LoyaltyPointReader {
    LoyaltyPoints getLoyaltyPoints(String customerId);
}
