package fr.sweetiez.api.core.loyalty.points.models.loyatypoints;

public record Points(Integer points) {

    public boolean isValid() {
        return points != null && points > 0;
    }

}
