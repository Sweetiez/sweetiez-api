package fr.sweetiez.api.core.loyalty.points.models.loyatypoints;

import fr.sweetiez.api.core.loyalty.points.models.requests.AddLoyaltyPointsRequest;

public record LoyaltyPoints(String customerId, Points points) {

    public LoyaltyPoints(AddLoyaltyPointsRequest request) {
        this(
            request.customerId(),
            new Points(request.orderPrice().intValue())
        );
    }

    public boolean isValid() {
        return customerId != null && points.isValid();
    }

    public LoyaltyPoints add(LoyaltyPoints points) {
        return new LoyaltyPoints(customerId, new Points(this.points.points() + points.points.points()));
    }

    public LoyaltyPoints minus(int cost) {
        return new LoyaltyPoints(customerId, new Points(this.points.points() - cost));
    }
}
