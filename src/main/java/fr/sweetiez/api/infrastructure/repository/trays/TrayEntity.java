package fr.sweetiez.api.infrastructure.repository.trays;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationEntity;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="sweet")
public class TrayEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private final String name;

    @Column(columnDefinition = "text")
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

    @Column(columnDefinition = "text")
    private String images;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<SweetEntity> sweets;

    @OneToMany
    private final List<EvaluationEntity> evaluations;

    public TrayEntity() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.highlight = null;
        this.state = null;
        this.flavor = null;
        this.images = null;
        this.sweets = null;
        this.evaluations = null;
    }

    public TrayEntity(UUID id, String name, String description, BigDecimal price, Highlight highlight, State state, Flavor flavor, String images, List<SweetEntity> sweets, List<EvaluationEntity> evaluations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.highlight = highlight;
        this.state = state;
        this.flavor = flavor;
        this.images = images;
        this.sweets = sweets;
        this.evaluations = evaluations;
    }

    public UUID getId() {
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

    public String getImages() {
        return images;
    }

    public List<SweetEntity> getSweets() {
        return sweets;
    }

    public List<EvaluationEntity> getEvaluations() {
        return evaluations;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrayEntity that = (TrayEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && highlight == that.highlight && state == that.state && flavor == that.flavor && Objects.equals(images, that.images);
    }

    public void addImage(String imageUrl) {
        this.images += String.format("%s;", imageUrl);
    }

    public int hashCode() {
        return Objects.hash(id, name, description, price, highlight, state, flavor, images);
    }

    public String toString() {
        return "TrayEntity{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", highlight=" + highlight +
                ", state=" + state +
                ", flavor=" + flavor +
                ", images='" + images + '\'' +
                '}';
    }
}
