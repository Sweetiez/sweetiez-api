package fr.sweetiez.products.sweets.domain;

import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.Flavor;
import fr.sweetiez.products.common.State;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SweetValidatorTest {

    private SweetValidator sut;


    @ParameterizedTest
    @MethodSource("provideInvalidSweets")
    void shouldDetectErrorsWhenItemHasInvalidFields(Sweet sweet) {
        sut = new SweetValidator(sweet);
        assertTrue(sut.hasErrors());
    }

    @ParameterizedTest
    @MethodSource("provideValidSweets")
    void shouldNotDetectErrorsWhenItemHasValidFields(Sweet sweet) {
        sut = new SweetValidator(sweet);
        assertFalse(sut.hasErrors());
    }

    @ParameterizedTest
    @MethodSource("provideValidSweets")
    void shouldNotContainErrorsWhenItemHasValidFields(Sweet sweet) {
        sut = new SweetValidator(sweet);
        sut.hasErrors();
        assertTrue(sut.getErrors().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSweets")
    void shouldContainErrorsWhenItemHasInvalidFields(Sweet sweet) {
        sut = new SweetValidator(sweet);
        sut.hasErrors();
        assertFalse(sut.getErrors().isEmpty());
    }

    private static Stream<Sweet> provideInvalidSweets() {
        return Stream.of(
                new Sweet.Builder().build(),
                new Sweet.Builder().name("").build(),
                new Sweet.Builder().name("invalid name").build(),
                new Sweet.Builder()
                        .name("Valid name")
                        .price(BigDecimal.valueOf(-1.))
                        .build(),
                new Sweet.Builder()
                        .name("Valid name")
                        .price(BigDecimal.valueOf(1.))
                        .ingredients(new HashSet<>())
                        .build(),
                new Sweet.Builder()
                        .name("Valid name")
                        .price(BigDecimal.valueOf(1.))
                        .ingredients(new HashSet<>(List.of("")))
                        .build()
        );
    }

    private static Stream<Sweet> provideValidSweets() {
        return Stream.of(
                new Sweet.Builder()
                        .id(UUID.randomUUID())
                        .name("Valid name")
                        .price(BigDecimal.valueOf(0.01))
                        .ingredients(new HashSet<>(List.of("apple", "banana", "chocolate")))
                        .state(State.CREATED)
                        .highlight(Highlight.COMMON)
                        .flavor(Flavor.SWEET)
                        .build(),

                new Sweet.Builder()
                        .id(UUID.randomUUID())
                        .name("Another valid name")
                        .price(BigDecimal.valueOf(34.99))
                        .ingredients(new HashSet<>(List.of("apple", "banana", "chocolate")))
                        .state(State.PUBLISHED)
                        .highlight(Highlight.PROMOTED)
                        .flavor(Flavor.SALTY)
                        .build(),

                new Sweet.Builder()
                        .id(UUID.randomUUID())
                        .name("Another valid name")
                        .price(BigDecimal.valueOf(34.99))
                        .ingredients(new HashSet<>(List.of("apple", "banana", "chocolate")))
                        .state(State.NON_PUBLISHED)
                        .highlight(Highlight.BANNER)
                        .flavor(Flavor.SALTY)
                        .build()
        );
    }
}