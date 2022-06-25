package fr.sweetiez.api.infrastructure.repository.orders;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final String firstName;

    @Column(nullable = false)
    private final String lastName;

    @Column(nullable = false)
    private final String email;

    @Column(nullable = false)
    private final String phone;

    @Column(nullable = false)
    private final double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column()
    private final String customerId;

    @Column()
    private final LocalDate pickupDate;

    @Column(nullable = false)
    private final LocalDate createdAt;

    @Column(columnDefinition = "text")
    private final String paymentIntent;

    @Column()
    private final String rewardId;

    public OrderEntity() {
        this.id = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
        this.totalPrice = 0;
        this.status = null;
        this.customerId = null;
        this.pickupDate = null;
        this.createdAt = null;
        this.paymentIntent = null;
        this.rewardId = null;
    }

    public OrderEntity(UUID id, String firstName,
                       String lastName, String email,
                       String phone, double totalPrice,
                       OrderStatus status, String customerId,
                       LocalDate pickupDate, LocalDate createdAt,
                       String paymentIntent, String rewardId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerId = customerId;
        this.pickupDate = pickupDate;
        this.createdAt = createdAt;
        this.paymentIntent = paymentIntent;
        this.rewardId = rewardId;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public String getRewardId() {
        return rewardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Double.compare(that.totalPrice, totalPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && status == that.status && Objects.equals(customerId, that.customerId) && Objects.equals(pickupDate, that.pickupDate) && Objects.equals(createdAt, that.createdAt) && Objects.equals(paymentIntent, that.paymentIntent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, totalPrice, status, customerId, pickupDate, createdAt, paymentIntent);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", customerId='" + customerId + '\'' +
                ", pickupDate=" + pickupDate +
                ", createdAt=" + createdAt +
                ", paymentIntent='" + paymentIntent + '\'' +
                '}';
    }
}
