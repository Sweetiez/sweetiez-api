package fr.sweetiez.api.account;

import java.util.Objects;

public class CustomerAccount {
    private final String id;
    private Long loyaltyPoints;

    public CustomerAccount(String id, Long loyaltyPoints) {
        this.id = id;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getId() {
        return id;
    }

    public Long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAccount that = (CustomerAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(loyaltyPoints, that.loyaltyPoints);
    }

    public int hashCode() {
        return Objects.hash(id, loyaltyPoints);
    }

    public String toString() {
        return "CustomerAccount{" +
                "id='" + id + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                '}';
    }
}
