package fr.sweetiez.sweets.domain;

import fr.sweetiez.sweets.domain.exceptions.InvalidPriceException;
import fr.sweetiez.sweets.domain.exceptions.InvalidSweetNameException;
import fr.sweetiez.sweets.domain.exceptions.SweetAlreadyExistsException;
import fr.sweetiez.sweets.exposition.SweetDTO;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Sweet {
    private final SweetID id;
    private final String name;
    private final Set<String> ingredients;
    private final BigDecimal price;

    public Sweet(SweetDTO sweet, Set<Sweet> sweets) {
        checkValidity(sweet, sweets);

        this.id = getValidRandomID(sweets);
        this.name = sweet.getName();
        this.ingredients = sweet.getIngredients();
        this.price = sweet.getPrice();

    }

    public void checkValidity(SweetDTO sweet, Set<Sweet> sweets) {
        checkNameValidity(sweet.getName(), sweets);
        checkPriceValidity(sweet.getPrice());
    }

    public SweetID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void checkNameValidity(String name, Set<Sweet> sweets) {
        if (name.isEmpty() || !name.matches("^[A-Za-z][ A-Za-z]+$")) throw new InvalidSweetNameException();

        boolean nameAlreadyExists = sweets.stream().anyMatch(sweet -> sweet.getName().equals(name));
        if (nameAlreadyExists) throw new SweetAlreadyExistsException();
    }

    private void checkPriceValidity(BigDecimal price) {
        if (price.doubleValue() <= 0) throw new InvalidPriceException();
    }

    private SweetID getValidRandomID(Set<Sweet> ids) {
        boolean idAlreadyExists;
        SweetID validId;

        do {
            var randomId = new SweetID(UUID.randomUUID());
            validId = new SweetID(UUID.randomUUID());

            idAlreadyExists = ids.stream()
                    .anyMatch(sweet -> sweet.getId().equals(randomId));

        } while (idAlreadyExists);

        return validId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sweet sweet = (Sweet) o;
        return id.equals(sweet.id) && name.equals(sweet.name) && ingredients.equals(sweet.ingredients);
    }

    public int hashCode() {
        return Objects.hash(id, name, ingredients);
    }

    public String toString() {
        return "Sweet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients + '\'' +
                ", price=" + price + '\'' +
                '}';
    }
}
