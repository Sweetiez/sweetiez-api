package fr.sweetiez.api.infrastructure.repository.reward;

import fr.sweetiez.api.core.orders.models.orders.products.ProductType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rewards")
public class RewardEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column()
    private final String rewardName;

    @Column()
    private final Integer cost;

    @Column()
    private final String productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final ProductType productType;

    public RewardEntity() {
        this.id = null;
        this.rewardName = null;
        this.cost = null;
        this.productId = null;
        this.productType = null;
    }

    public RewardEntity(UUID id, String rewardName, Integer cost, String productId, ProductType productType) {
        this.id = id;
        this.rewardName = rewardName;
        this.cost = cost;
        this.productId = productId;
        this.productType = productType;
    }

    public UUID getId() {
        return id;
    }

    public String getRewardName() {
        return rewardName;
    }

    public Integer getCost() {
        return cost;
    }

    public String getProductId() {
        return productId;
    }

    public ProductType getProductType() {
        return productType;
    }
}
