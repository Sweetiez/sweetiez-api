package fr.sweetiez.sweets.use_cases;

import java.util.Objects;

public class IngredientDTO {
    private final String name;
    private final Integer quantity;

    public IngredientDTO(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDTO that = (IngredientDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity);
    }

    public int hashCode() {
        return Objects.hash(name, quantity);
    }

    public String toString() {
        return "IngredientDTO{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
