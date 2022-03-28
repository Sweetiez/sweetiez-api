package fr.sweetiez.sweets.domain;

import fr.sweetiez.sweets.domain.exceptions.InvalidIngredientsException;
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
    private final Status status;
    private final String name;
    private final Set<String> ingredients;
    private final BigDecimal price;

    public Sweet(SweetDTO sweet, Set<Sweet> sweets) {
        checkValidity(sweet, sweets);

        id = getValidRandomID(sweets);
        status = Status.CREATED;
        name = sweet.getName();
        ingredients = sweet.getIngredients();
        price = sweet.getPrice();

    }

    public void checkValidity(SweetDTO sweet, Set<Sweet> sweets) {
        checkNameValidity(sweet.getName(), sweets);
        checkPriceValidity(sweet.getPrice());
        checkIngredientsValidity(sweet.getIngredients());
    }

    public SweetID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void checkNameValidity(String name, Set<Sweet> sweets) {
        if (name == null || name.isEmpty() || !name.matches("^[A-Za-z][ A-Za-z]+$"))
            throw new InvalidSweetNameException();

        boolean nameAlreadyExists = sweets.stream().anyMatch(sweet -> sweet.getName().equals(name));
        if (nameAlreadyExists) throw new SweetAlreadyExistsException();
    }

    private void checkPriceValidity(BigDecimal price) {
        if (price.doubleValue() <= 0) throw new InvalidPriceException();
    }

    private void checkIngredientsValidity(Set<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) throw new InvalidIngredientsException();

        for (var ingredient : ingredients) {
            if (ingredient == null || ingredient.isEmpty()) throw new InvalidIngredientsException();
        }
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
        return Objects.equals(id, sweet.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return "Sweet{" +
                "id=" + id +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", price=" + price +
                '}';
    }
}
