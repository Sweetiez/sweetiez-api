package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.details.Details;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.details.Name;
import fr.sweetiez.api.core.sweets.models.sweet.details.Price;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;
import fr.sweetiez.api.infrastructure.repository.SweetEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SweetMapperTest {

    private final SweetMapper sut = new SweetMapper();

    @Test
    void shouldConvertModelToEntity() {
        String sweetId = UUID.randomUUID().toString();
        var sweet = new Sweet(
                new SweetId(sweetId),
                new Name("Sweet name"),
                new Price(BigDecimal.valueOf(1.)),
                new States(Highlight.COMMON, State.CREATED),
                new Details(
                        "Sweet description",
                        Flavor.SWEET,
                        List.of("a", "b"),
                        new Ingredients(Set.of()),
                        5.)
        );

        var expected = new SweetEntity(
                null,
                sweetId,
                "Sweet name",
                "Sweet description",
                BigDecimal.valueOf(1.),
                Highlight.COMMON,
                State.CREATED,
                Flavor.SWEET,
                "a;b;"
        );

        var result = sut.toEntity(sweet);

        assertEquals(expected, result);
    }

    @Test
    void shouldConvertEntityToModel() {
        String sweetId = UUID.randomUUID().toString();

        var sweetEntity = new SweetEntity(
                null,
                sweetId,
                "Sweet name",
                "Sweet description",
                BigDecimal.valueOf(1.),
                Highlight.COMMON,
                State.CREATED,
                Flavor.SWEET,
                "a;b;"
        );

        var expected = new Sweet(
                new SweetId(sweetId),
                new Name("Sweet name"),
                new Price(BigDecimal.valueOf(1.)),
                new States(Highlight.COMMON, State.CREATED),
                new Details(
                        "Sweet description",
                        Flavor.SWEET,
                        List.of("a", "b"),
                        new Ingredients(Set.of()),
                        5.)
        );

        var result = sut.toDto(sweetEntity);

        assertEquals(expected, result);
    }
}