package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.FakeSweetRepository;
import fr.sweetiez.sweets.domain.*;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublishSweetTest {
    private final SweetFaker sweetFaker;
    private PublishSweet authorizedEmployee;
    private Set<Sweet> createdSweets;

    public PublishSweetTest() {
        sweetFaker = new SweetFaker();
    }

    @BeforeEach
    public void init() {
        var fakeSweetRepository = new FakeSweetRepository();

        for (int i = 0; i < 3; i++) {
            var name = Character.toString('a' + i);
            var sweet = sweetFaker.fakeSweet("a" + name);
            fakeSweetRepository.save(sweet);
        }

        createdSweets = fakeSweetRepository.all();

        authorizedEmployee = new PublishSweet(fakeSweetRepository);
    }

    @Test
    public void shouldChangeTheStatusOfGivenSweetToPublished() {
        var sweet = createdSweets.iterator().next();
        assertEquals(Status.CREATED, sweet.getStatus());

        var updatedSweet = authorizedEmployee.publish(sweet.getId().toString(), Priority.COMMON);
        assertEquals(Status.PUBLISHED, updatedSweet.getStatus());
    }

    @Test
    public void shouldNotChangeTheStatusOfGivenSweetToPublishedIfItsStatusIsAlreadyPublished() {
        var sweet = createdSweets.iterator().next();
        assertEquals(Status.CREATED, sweet.getStatus());

        var updatedSweet = authorizedEmployee.publish(sweet.getId().toString(), Priority.COMMON);
        assertEquals(Status.PUBLISHED, updatedSweet.getStatus());

        var updatedSweetSecondTime = authorizedEmployee.publish(sweet.getId().toString(), Priority.TOP);
        assertEquals(Status.PUBLISHED, updatedSweetSecondTime.getStatus());
    }

    @Test
    public void shouldAlertThatAnySweetHasBeenFound() {
        String id = UUID.randomUUID().toString();
        ThrowingCallable publishSweet = () -> authorizedEmployee.publish(id, Priority.COMMON);
        assertThatExceptionOfType(AnySweetFoundException.class).isThrownBy(publishSweet);
    }
}
