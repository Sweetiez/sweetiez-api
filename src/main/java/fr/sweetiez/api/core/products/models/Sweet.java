package fr.sweetiez.api.core.products.models;

import fr.sweetiez.api.core.ingredients.models.HealthProperty;
import fr.sweetiez.api.core.ingredients.models.HealthPropertyType;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.core.products.models.requests.CreateProductRequest;
import fr.sweetiez.api.core.products.models.requests.UpdateProductRequest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Sweet extends Product {
    private final Collection<Ingredient> ingredients;

    public Sweet(ProductID id, Name name, Description description, Price price,
                 Details details, Collection<Ingredient> ingredients) {
        super(id, name, description, price, details);
        this.ingredients = ingredients;
    }

    public Sweet(CreateProductRequest request, Collection<Ingredient> ingredients) {
        super(new ProductID(null),
                new Name(request.name()),
                new Description(request.description()),
                new Price(request.price()),
                new Details(
                        List.of(),
                        new Characteristics(Highlight.COMMON, State.CREATED, request.flavor()),
                        new Valuation(List.of())));

        this.ingredients = ingredients;
    }

    public Sweet(Sweet sweet, Details details) {
        super(sweet.id(),
                sweet.name(),
                sweet.description(),
                sweet.price(),
                details);

        ingredients = sweet.ingredients();
    }

    public Sweet(Sweet sweet, UpdateProductRequest request, Collection<Ingredient> ingredients) {
        super(sweet.id,
                new Name(request.name()),
                new Description(request.description()),
                new Price(request.price()),
                new Details(
                        request.images(),
                        new Characteristics(request.highlight(), request.state(), request.flavor()),
                        sweet.details().valuation()));

        this.ingredients = ingredients;
    }

    public boolean isValid() {
        return super.isValid() && ingredients != null && !ingredients.isEmpty();
    }

    public Sweet publish(Highlight highlight) {
        if (!isValid()) {
            return this;
        }

        var characteristics = new Characteristics(highlight, State.PUBLISHED, details.characteristics().flavor());
        var details = new Details(details().images(), characteristics, details().valuation());

        return new Sweet(this, details);
    }

    public Sweet unpublish() {
        if (!isValid()) {
            return this;
        }

        var characteristics = new Characteristics(
                details.characteristics().highlight(),
                State.NON_PUBLISHED,
                details.characteristics().flavor());

        var details = new Details(details().images(), characteristics, details().valuation());

        return new Sweet(this, details);
    }

    public Collection<Ingredient> ingredients() {
        return ingredients;
    }

    public Collection<String> diets() {
        var ingredientsDiets = ingredientsDietLabels();
        return computeDiets(ingredientsDiets);
    }

    private Collection<List<String>> ingredientsDietLabels() {
        return ingredients.stream()
                .map(Ingredient::healthProperties)
                .map(properties -> properties
                        .stream()
                        .filter(property -> property.type().equals(HealthPropertyType.DIET))
                        .map(HealthProperty::name)
                        .toList())
                .toList();
    }

    public Collection<String> allergens() {
        return ingredients.stream()
                .map(Ingredient::healthProperties)
                .flatMap(Collection::stream)
                .filter(property -> property.type().equals(HealthPropertyType.ALLERGEN))
                .map(HealthProperty::name)
                .distinct()
                .toList();
    }

    public Sweet addImage(String imageUrl) {
        var newDetails = details.addImage(imageUrl);
        return new Sweet(this, newDetails);
    }

    public Sweet deleteImage(String imageUrl) {
        var newDetails = details.deleteImage(imageUrl);
        return new Sweet(this, newDetails);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sweet sweet = (Sweet) o;
        return ingredients.equals(sweet.ingredients);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), ingredients);
    }

    public String toString() {
        return "Sweet{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", price=" + price +
                ", details=" + details +
                ", ingredients=" + ingredients +
                '}';
    }
}
