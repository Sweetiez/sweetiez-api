package fr.sweetiez.api.infrastructure.repository;

import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="sweet")
public class SweetEntity {

    @Id
    @GeneratedValue
    private final Long dbId;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private final String id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private final String name;

    private final String description;

    @Column(nullable = false)
    @NotNull
    private final BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Highlight highlight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final State state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Flavor flavor;

    private final String imageUrl;

    public SweetEntity() {
        this.dbId = null;
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.highlight = null;
        this.state = null;
        this.flavor = null;
        this.imageUrl = null;
    }

    public SweetEntity(Long dbId, String id, String name, String description, BigDecimal price, Highlight highlight, State state, Flavor flavor, String imageUrl) {
        this.dbId = dbId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.highlight = highlight;
        this.state = state;
        this.flavor = flavor;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Highlight getHighlight() {
        return highlight;
    }

    public State getState() {
        return state;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SweetEntity that = (SweetEntity) o;
        return Objects.equals(dbId, that.dbId) && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && highlight == that.highlight && state == that.state && flavor == that.flavor && Objects.equals(imageUrl, that.imageUrl);
    }

    public int hashCode() {
        return Objects.hash(dbId, id, name, description, price, highlight, state, flavor, imageUrl);
    }

    @Override
    public String toString() {
        return "SweetEntity{" +
                "dbId=" + dbId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", highlight=" + highlight +
                ", state=" + state +
                ", flavor=" + flavor +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
