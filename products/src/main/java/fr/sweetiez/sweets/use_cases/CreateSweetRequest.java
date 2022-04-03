package fr.sweetiez.sweets.use_cases;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class CreateSweetRequest {
    private final String name;
    private final Set<String> ingredients;
    private final BigDecimal price;

    public CreateSweetRequest(String name, Set<String> ingredients, BigDecimal price) {
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

        CreateSweetRequest request = (CreateSweetRequest) o;

        if (!Objects.equals(name, request.name)) return false;
        return Objects.equals(ingredients, request.ingredients);
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
