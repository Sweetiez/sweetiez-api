package fr.sweetiez.exposition;

import fr.sweetiez.fakers.FakeSweetRepository;
import fr.sweetiez.fakers.FakeSweetDTO;
import fr.sweetiez.fakers.SweetFaker;
import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.use_cases.PublishSweetRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SweetControllerTest {
    private final FakeSweetDTO fakeSweetDTO;
    private final SweetFaker sweetFaker;
    private SweetController sweetController;

    public SweetControllerTest() {
        fakeSweetDTO = new FakeSweetDTO();
        sweetFaker = new SweetFaker();
    }

    @BeforeEach
    public void init() {
        var fakeSweetRepository = new FakeSweetRepository();
        sweetController = new SweetController(fakeSweetRepository);
    }

    private String getValidID() {
        String id = "";
        var fakeSweetRepository = new FakeSweetRepository();

        for (int i = 0; i < 3; i++) {
            var name = Character.toString('a' + i);
            var sweet = sweetFaker.fakeSweet("a" + name);
            fakeSweetRepository.save(sweet,UUID.randomUUID());
            if (i == 0) id = sweet.getId().toString();
        }

        sweetController = new SweetController(fakeSweetRepository);
        return id;
    }

    @Test
    public void shouldCreateSweetAndReturnResponseWithStatus201() {
        var fakeSweet = fakeSweetDTO.createValidSweetDTO();
        var response = sweetController.create(fakeSweet);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    }

    @Test
    public void shouldNotCreateSweetAndReturnResponseWithStatus400() {
        var fakeSweet = fakeSweetDTO.withEmptyName();
        var response = sweetController.create(fakeSweet);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    public void shouldReturnResponseWithEmptyListOfPublishedSweets() {
        var response = sweetController.findAllPublished();
        var sweets = response.getBody();

        assertNotNull(sweets);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertTrue(sweets.isEmpty());
    }

    @Test
    public void shouldNotPublishTheSweetAndReturnResponseWithStatus404() {
        PublishSweetRequest request = new PublishSweetRequest(UUID.randomUUID(), Priority.COMMON);
        var response = sweetController.publish(request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldPublishTheSweetAndReturnResponseWithStatus200() {
        String id = getValidID();
        var request = new PublishSweetRequest(UUID.fromString(id), Priority.CAROUSEL);
        var response = sweetController.publish(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
