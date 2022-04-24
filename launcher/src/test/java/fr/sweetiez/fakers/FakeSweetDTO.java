package fr.sweetiez.fakers;

import fr.sweetiez.sweets.domain.SweetType;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

public class FakeSweetDTO {
    public FakeSweetDTO() {}

    public CreateSweetRequest createValidSweetDTO() {
        var name = "Sweet name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest copyOf(CreateSweetRequest dto) {
        return new CreateSweetRequest(dto.getName(), dto.getIngredients(),dto.getDescription(), dto.getPrice(), dto.getType(), dto.getCreator());
    }

    public CreateSweetRequest withEmptyName() {
        var name = "";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients,  description, price, type, creator);
    }

    public CreateSweetRequest withInvalidName() {
        var name = "F1s€3t$";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withNegativePrice() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(-1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withPriceEqualsZero() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(0);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withPriceEqualsNull() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, null, type, creator);
    }

    public CreateSweetRequest withNameEqualsNull() {
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(0);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }
        return new CreateSweetRequest(null, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withIngredientsEqualsNull() {
        var name = "Valid name";
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        return new CreateSweetRequest(name, null, description, price, type, creator);
    }

    public CreateSweetRequest withEmptyIngredients() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withIngredientsContainingNullValue() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                ingredients.add(null);
            } else ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }

    public CreateSweetRequest withIngredientsContainingEmptyValue() {
        var name = "Valid name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);
        var description = "Sweet description";
        var type = SweetType.SWEET;
        var creator = UUID.randomUUID();

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                ingredients.add("");
            } else ingredients.add(RandomString.make());
        }

        return new CreateSweetRequest(name, ingredients, description, price, type, creator);
    }
}
