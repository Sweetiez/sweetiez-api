package fr.sweetiez.api.infrastructure.repository.orders;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_detail")
public class OrderDetailEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(nullable = false)
    private final UUID productId;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final int quantity;

    @Column(nullable = false)
    private final double unitPrice;

    @Column(nullable = false)
    private final UUID orderId;

    public OrderDetailEntity() {
        this.id = null;
        this.orderId = null;
        this.productId = null;
        this.name = null;
        this.quantity = 0;
        this.unitPrice = 0;
    }

    public OrderDetailEntity(UUID id, UUID orderId, UUID productId, String name, int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}

