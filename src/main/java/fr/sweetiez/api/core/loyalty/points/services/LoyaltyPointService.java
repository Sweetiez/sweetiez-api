package fr.sweetiez.api.core.loyalty.points.services;

import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.LoyaltyPoints;
import fr.sweetiez.api.core.loyalty.points.models.requests.AddLoyaltyPointsRequest;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointReader;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointWriter;
import fr.sweetiez.api.core.loyalty.points.services.exceptions.InvalidLoyaltyPointsException;

public class LoyaltyPointService {

    private final LoyaltyPointReader reader;
    private final LoyaltyPointWriter writer;

    public LoyaltyPointService(LoyaltyPointReader reader, LoyaltyPointWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public LoyaltyPoints addLoyaltyPoints(AddLoyaltyPointsRequest request) {
        var newLoyaltyPoints = new LoyaltyPoints(request);

        // Get customer loyalty points
        var customerLoyaltyPoints = reader.getLoyaltyPoints(newLoyaltyPoints.customerId());

        // Add new loyalty points to customer loyalty points
        var updatedLoyaltyPoints = customerLoyaltyPoints.add(newLoyaltyPoints);

        return writer.save(updatedLoyaltyPoints);
    }
}
