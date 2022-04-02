package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.FakeSweetRepository;
import fr.sweetiez.sweets.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

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

        var updatedSweet = authorizedEmployee.publish(sweet.getId().toString(), Priority.COMMON);
        assertEquals(Status.PUBLISHED, updatedSweet.getStatus());
    }
}
