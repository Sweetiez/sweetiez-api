package fr.sweetiez.api.infrastructure.repository;

import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private String imageUrl;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false, updatable = false)
    private String creator;

    @UpdateTimestamp
    private LocalDateTime updated;

    @Column(name = "update_author")
    private String updateAuthor;
}
