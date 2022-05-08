package fr.sweetiez.products.sweets.domain;

import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.Flavor;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.exposition.CreateSweetRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class Sweet {
    private final UUID id;
    private final State state;
    private final Highlight highlight;
    private final String name;
    private final Set<String> ingredients;
    private final BigDecimal price;
    private final String description;
    private final Flavor flavor;

    public Sweet(CreateSweetRequest sweet) {
        id = null;
        state = State.CREATED;
        highlight = Highlight.COMMON;
        name = sweet.getName();
        ingredients = sweet.getIngredients();
        price = sweet.getPrice();
        description = sweet.getDescription();
        flavor = sweet.getType();
    }

    public Sweet(Sweet sweet, Highlight highlight) {
        id = sweet.getId();
        state = State.PUBLISHED;
        this.highlight = highlight;
        name = sweet.getName();
        ingredients = sweet.getIngredients();
        price = sweet.getPrice();
        description = sweet.getDescription();
        flavor = sweet.getFlavor();
    }

    private Sweet(Builder builder) {
        this.id = builder.id;
        this.state = builder.state;
        this.highlight = builder.highlight;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
        this.price = builder.price;
        this.description = builder.description;
        this.flavor = builder.flavor;
    }

    public static class Builder {
        private UUID id;
        private State state;
        private Highlight highlight;
        private String name;
        private Set<String> ingredients;
        private BigDecimal price;
        private String description;
        private Flavor flavor;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder highlight(Highlight highlight) {
            this.highlight = highlight;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ingredients(Set<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder flavor(Flavor flavor) {
            this.flavor = flavor;
            return this;
        }

        public Sweet build() {
            return new Sweet(this);
        }
    }
}
