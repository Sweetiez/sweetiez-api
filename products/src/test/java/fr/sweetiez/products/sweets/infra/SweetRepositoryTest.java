package fr.sweetiez.products.sweets.infra;

import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.common.Flavor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
                .creator(UUID.randomUUID())
                .created(LocalDateTime.now())
                .build();

        sut.save(entity);

        Optional<SweetEntity> optionalEntity = sut.findById(id);

        assertTrue(optionalEntity.isPresent());
    }

    @Test
    void shouldThrowErrorWhenSweetEntityDoesNotExists() {
        UUID id = UUID.randomUUID();
        Optional<SweetEntity> optionalEntity = sut.findById(id.toString());
        assertTrue(optionalEntity.isEmpty());
    }
}