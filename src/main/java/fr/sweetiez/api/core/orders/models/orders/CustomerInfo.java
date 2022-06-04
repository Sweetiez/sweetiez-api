package fr.sweetiez.api.core.orders.models.orders;

public record CustomerInfo(String firstName, String lastName, String email, String phone) {

    public boolean isValid() {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }
}
