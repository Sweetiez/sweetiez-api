package fr.sweetiez.sweets.fakers;

import fr.sweetiez.sweets.exposition.CreateSweetRequest;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.HashSet;

public class FakeSweetDTO {
    public FakeSweetDTO() {}

    public CreateSweetRequest createValidSweetDTO() {
        var name = "Sweet name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest copyOf(CreateSweetRequest dto) {
        return new CreateSweetRequest(dto.getName(), dto.getIngredients(), dto.getPrice());
    }

    public CreateSweetRequest withEmptyName() {
        var name = "";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withInvalidName() {
        var name = "F1s€3t$";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withNegativePrice() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(-1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withPriceEqualsZero() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(0);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withPriceEqualsNull() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, null);
    }

    public CreateSweetRequest withNameEqualsNull() {
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(0);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }
        return new CreateSweetRequest(null, ingredients, price);
    }

    public CreateSweetRequest withIngredientsEqualsNull() {
        var name = "Valid name";
        var price = BigDecimal.valueOf(1.95);

        return new CreateSweetRequest(name, null, price);
    }

    public CreateSweetRequest withEmptyIngredients() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withIngredientsContainingNullValue() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                ingredients.add(null);
            } else ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }

    public CreateSweetRequest withIngredientsContainingEmptyValue() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                ingredients.add("");
            } else ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, price);
    }
}
