package fr.sweetiez.api.customer.domain;

public class Customer {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }
}
