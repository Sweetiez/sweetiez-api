package fr.sweetiez.api.infrastructure.repository;

import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;
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

    @Test
    void shouldFindSweetEntityByDomainIdWhenExists() {

        String id = UUID.randomUUID().toString();
        SweetEntity entity = SweetEntity.builder()
                .id(id)
                .name("Sweet name")
                .price(BigDecimal.valueOf(1.99))
                .state(State.CREATED)
                .flavor(Flavor.SWEET)
                .highlight(Highlight.COMMON)
                .creator(UUID.randomUUID().toString())
                .build();

        sut.save(entity);

        Optional<SweetEntity> optionalEntity = sut.findById(id);

        assertTrue(optionalEntity.isPresent());
    }

    @Test
    void shouldReturnAnEmptyOptionalWhenSweetIdDoesNotExists() {
        var id = UUID.randomUUID().toString();
        Optional<SweetEntity> optionalEntity = sut.findById(id);
        assertTrue(optionalEntity.isEmpty());
    }

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
                SweetEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Created").price(BigDecimal.valueOf(1.))
                        .state(State.CREATED)
                        .creator(UUID.randomUUID().toString())
                        .flavor(Flavor.SWEET)
                        .highlight(Highlight.COMMON)
                        .build(),
                SweetEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Published").price(BigDecimal.valueOf(1.))
                        .state(State.PUBLISHED)
                        .creator(UUID.randomUUID().toString())
                        .flavor(Flavor.SWEET)
                        .highlight(Highlight.COMMON)
                        .build(),
                SweetEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Non published").price(BigDecimal.valueOf(1.))
                        .state(State.NON_PUBLISHED)
                        .creator(UUID.randomUUID().toString())
                        .flavor(Flavor.SWEET)
                        .highlight(Highlight.COMMON)
                        .creator("")
                        .build(),
                SweetEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Deleted").price(BigDecimal.valueOf(1.))
                        .state(State.DELETED)
                        .creator(UUID.randomUUID().toString())
                        .flavor(Flavor.SWEET)
                        .highlight(Highlight.COMMON)
                        .build()
        );
    }
}