package fr.sweetiez.api.core.sweets.models.sweet;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SweetTest {

    private Sweet sut;

    @Test
    void shouldReturnNewInstanceOfSweetWithHighlightAndStatePropertiesUpdated() {
        var request = new CreateSweetRequest(
                "Valid sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient(UUID.randomUUID(), "Valid ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        sut = new Sweet(new SweetId(UUID.randomUUID().toString()), request);

        sut = sut.publish(Highlight.PROMOTED);

        var highlight = sut.states().highlight();
        var state = sut.states().state();

        assertThat(state.equals(State.PUBLISHED) && highlight.equals(Highlight.PROMOTED))
                .isTrue();
    }

    @Test
    void shouldReturnCurrentInstanceWithoutAnyChangeApplied() {
        var request = new CreateSweetRequest(
                "invalid sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient(UUID.randomUUID(), "Valid ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        sut = new Sweet(new SweetId(UUID.randomUUID().toString()), request);

        sut = sut.publish(Highlight.PROMOTED);

        var highlight = sut.states().highlight();
        var state = sut.states().state();

        assertThat(state.equals(State.PUBLISHED) && highlight.equals(Highlight.PROMOTED))
                .isFalse();
    }

    @Test
    void shouldNotValidateSweet() {
        var request = new CreateSweetRequest(
                "Valid sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient(UUID.randomUUID(), "Valid ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        sut = new Sweet(new SweetId(UUID.randomUUID().toString()), request);
        assertTrue(sut.isValid());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateSweetRequestBody")
    void shouldNotValidateSweet(CreateSweetRequest request) {
        sut = new Sweet(new SweetId(UUID.randomUUID().toString()), request);
        assertFalse(sut.isValid());
    }

    public static Stream<CreateSweetRequest> provideInvalidCreateSweetRequestBody() {
        return Stream.of(
                new CreateSweetRequest(
                        "sweet name",
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "",
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        null,
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(1.99),
                        null,
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(-1.45),
                        Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(0.),
                        Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                )
        );
    }
}