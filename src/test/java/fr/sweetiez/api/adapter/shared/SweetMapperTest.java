package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SweetMapperTest {

    private final SweetMapper sut = new SweetMapper(
            new IngredientMapper(),
            new EvaluationMapper(new CustomerMapper(new AccountMapper())));

    @Test
    void shouldConvertModelToEntity() {
        UUID sweetId = UUID.randomUUID();
        var sweet = new Sweet(
                new ProductID(sweetId),
                new Name("Sweet name"),
                new Description("Sweet description"),
                new Price(BigDecimal.valueOf(1.)),
                new Details(
                        List.of("a", "b"),
                        new Characteristics(Highlight.COMMON, State.CREATED, Flavor.SWEET),
                        new Valuation(List.of())),
                List.of()
        );

        var expected = new SweetEntity(
                sweetId,
                "Sweet name",
                "Sweet description",
                BigDecimal.valueOf(1.),
                Highlight.COMMON,
                State.CREATED,
                Flavor.SWEET,
                "a;b;",
                List.of(),
                List.of()
        );

        var result = sut.toEntity(sweet);

        assertEquals(expected, result);
    }

    @Test
    void shouldConvertEntityToModel() {
        UUID sweetId = UUID.randomUUID();

        var sweetEntity = new SweetEntity(
                sweetId,
                "Sweet name",
                "Sweet description",
                BigDecimal.valueOf(1.),
                Highlight.COMMON,
                State.CREATED,
                Flavor.SWEET,
                "a;b;",
                List.of(),
                List.of()
        );

        var expected = new Sweet(
                new ProductID(sweetId),
                new Name("Sweet name"),
                new Description("Sweet description"),
                new Price(BigDecimal.valueOf(1.)),
                new Details(
                        List.of("a", "b"),
                        new Characteristics(Highlight.COMMON, State.CREATED, Flavor.SWEET),
                        new Valuation(List.of())),
                List.of()
        );

        var result = sut.toDto(sweetEntity);

        assertEquals(expected, result);
    }
}