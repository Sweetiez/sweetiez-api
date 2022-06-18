package fr.sweetiez.api.infrastructure.repository.products.trays;

import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetEntity;

import javax.persistence.*;

@Entity
@Table(name = "sweet_quantity")
public class SweetWithQuantityEntity {

    @Id
    @GeneratedValue
    private final Long id;

    @OneToOne
    private final SweetEntity sweet;

    private final Integer quantity;

    public SweetWithQuantityEntity() {
        id = null;
        sweet = null;
        quantity = null;
    }

    public SweetWithQuantityEntity(Long id, SweetEntity sweet, Integer quantity) {
        this.id = id;
        this.sweet = sweet;
        this.quantity = quantity;
    }

    public Long id() {
        return id;
    }

    public SweetEntity sweet() {
        return sweet;
    }

    public Integer quantity() {
        return quantity;
    }
}