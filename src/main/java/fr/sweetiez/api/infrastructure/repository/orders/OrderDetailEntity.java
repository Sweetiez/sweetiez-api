package fr.sweetiez.api.infrastructure.repository.orders;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;

import javax.persistence.*;
import java.util.Objects;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final ProductType productType;

    public OrderDetailEntity() {
        this.id = null;
        this.orderId = null;
        this.productId = null;
        this.name = null;
        this.quantity = 0;
        this.unitPrice = 0;
        this.productType = null;
    }

    public OrderDetailEntity(UUID id, UUID orderId, UUID productId, String name, int quantity, double unitPrice, ProductType productType) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productType = productType;
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

    public ProductType getProductType() {
        return productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailEntity that = (OrderDetailEntity) o;
        return quantity == that.quantity && Double.compare(that.unitPrice, unitPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(orderId, that.orderId) && productType == that.productType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, quantity, unitPrice, orderId, productType);
    }

    @Override
    public String toString() {
        return "OrderDetailEntity{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", orderId=" + orderId +
                ", productType=" + productType +
                '}';
    }
}

