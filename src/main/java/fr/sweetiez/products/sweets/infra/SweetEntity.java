package fr.sweetiez.products.sweets.infra;

import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.domain.Sweet;
import fr.sweetiez.products.common.Flavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="sweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SweetEntity {
    @Id
    @GeneratedValue
    private Long dbId;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String name;

    private String description;

    @Column(nullable = false)
    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Highlight highlight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Flavor flavor;

//    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private UUID creator;

    private LocalDateTime updated;

    @Column(name = "update_author")
    private UUID updateAuthor;

    public SweetEntity(Sweet sweet, UUID employee) {
        name = sweet.getName();
        description = sweet.getDescription();
        state = sweet.getState();
        highlight = sweet.getHighlight();
        flavor = sweet.getFlavor();
        price = sweet.getPrice();
        created = LocalDateTime.now();
        creator = employee;
    }

    public Sweet toSweet() {
        return new Sweet.Builder()
                .id(UUID.fromString(id))
                .description(description)
                .name(name)
                .state(state)
                .highlight(highlight)
                .flavor(flavor)
                .price(price)
                .build();
    }
}
