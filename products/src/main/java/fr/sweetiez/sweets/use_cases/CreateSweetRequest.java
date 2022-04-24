package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.SweetType;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CreateSweetRequest {
    private final String name;
    private final Set<String> ingredients;
    private final String description;
    private final SweetType type;
    private final BigDecimal price;

    private final UUID creator;

    public CreateSweetRequest(String name, Set<String> ingredients, String description, BigDecimal price, SweetType type, UUID creator) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.type = type;
        this.price = price;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }

    public SweetType getType() {
        return type;
    }

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

    public UUID getCreator() {
        return creator;
    }
}
