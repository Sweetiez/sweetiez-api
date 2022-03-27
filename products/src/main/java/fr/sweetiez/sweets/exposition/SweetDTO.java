package fr.sweetiez.sweets.exposition;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class SweetDTO {
    private final String name;
    private final Set<String> ingredients;
    private final BigDecimal price;

    public SweetDTO(String name, Set<String> ingredients, BigDecimal price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public BigDecimal getPrice() { return price; }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SweetDTO sweetDTO = (SweetDTO) o;

        if (!Objects.equals(name, sweetDTO.name)) return false;
        return Objects.equals(ingredients, sweetDTO.ingredients);
    }

    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "SweetDTO{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients + '\'' +
                ", price=" + price + '\'' +
                '}';
    }
}
