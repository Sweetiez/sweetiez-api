package fr.sweetiez.api.infrastructure.repository;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetEntity;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = SweetRepository.class)
class SweetRepositoryTest {

    @Autowired
    private SweetRepository sut;

    @ParameterizedTest
    @MethodSource("provideAllEnumerationsOfState")
    void findAllByState(State state, Set<SweetEntity> entitiesToSave) {
        sut.saveAll(entitiesToSave);
        var entitiesFound = sut.findAllByState(state);
        var containsCurrentOneState = entitiesFound.stream().allMatch(entity -> entity.getState().equals(state));
        assertTrue(containsCurrentOneState);
    }

    public static Stream<Arguments> provideAllEnumerationsOfState() {
        var entities = provideSweetEntityWithDifferentStates();
        return Stream.of(
                Arguments.of(State.CREATED, entities),
                Arguments.of(State.PUBLISHED, entities),
                Arguments.of(State.NON_PUBLISHED, entities),
                Arguments.of(State.DELETED, entities)
        );
    }

    private static Set<SweetEntity> provideSweetEntityWithDifferentStates() {
        return Set.of(
                new SweetEntity(
                        UUID.randomUUID(),
                        "CREATED",
                        "",
                        BigDecimal.ONE,
                        5,
                        Highlight.COMMON,
                        State.CREATED,
                        Flavor.SWEET,
                        "",
                        List.of(),
                        List.of()
                ),
                new SweetEntity(
                        UUID.randomUUID(),
                        "PUBLISHED",
                        "",
                        BigDecimal.ONE,
                        5,
                        Highlight.COMMON,
                        State.PUBLISHED,
                        Flavor.SWEET,
                        "",
                        List.of(),
                        List.of()
                ),
                new SweetEntity(
                        UUID.randomUUID(),
                        "NON_PUBLISHED",
                        "",
                        BigDecimal.ONE,
                        5,
                        Highlight.COMMON,
                        State.NON_PUBLISHED,
                        Flavor.SWEET,
                        "",
                        List.of(),
                        List.of()
                ),
                new SweetEntity(
                        UUID.randomUUID(),
                        "DELETED",
                        "",
                        BigDecimal.ONE,
                        5,
                        Highlight.COMMON,
                        State.DELETED,
                        Flavor.SWEET,
                        "",
                        List.of(),
                        List.of()
                )
        );
    }
}