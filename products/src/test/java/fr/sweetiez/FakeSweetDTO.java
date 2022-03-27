package fr.sweetiez;

import fr.sweetiez.sweets.exposition.SweetDTO;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.HashSet;

public class FakeSweetDTO {
    public FakeSweetDTO() {}

    public SweetDTO createValidSweetDTO() {
        var name = "Sweet name";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new SweetDTO(name, ingredients, price);
    }

    public SweetDTO copyOf(SweetDTO dto) {
        return new SweetDTO(dto.getName(), dto.getIngredients(), dto.getPrice());
    }

    public SweetDTO withEmptyName() {
        var name = "";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new SweetDTO(name, ingredients, price);
    }

    public SweetDTO withNumbersInName() {
        var name = "F1s€3t$";
        var ingredients = new HashSet<String>();
        var price = BigDecimal.valueOf(1.95);

        for (int i = 0; i < 3; i++) {
            ingredients.add(RandomString.make());
        }

        return new SweetDTO(name, ingredients, price);
    }
}
