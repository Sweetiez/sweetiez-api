package fr.sweetiez.api.usecases.payment;

public class CreditCard {

    private final String number;
    private final String holder;
    private final String expirationDate;
    private final Integer ccv;

    public CreditCard(String number, String holder, String expirationDate, Integer ccv) {
        this.number = number;
        this.holder = holder;
        this.expirationDate = expirationDate;
        this.ccv = ccv;
    }

    public boolean isValid() {
        return true;
    }

    public String toString() {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", holder='" + holder + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", ccv=" + ccv +
                '}';
    }
}
