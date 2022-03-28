package fr.sweetiez.sweets.exposition;

import fr.sweetiez.sweets.FakeSweetRepository;
import fr.sweetiez.sweets.use_cases.FakeSweetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class SweetControllerTest {
    private final FakeSweetDTO fakeSweetDTO;
    private SweetController sweetController;

    public SweetControllerTest() {
        fakeSweetDTO = new FakeSweetDTO();
    }

    @BeforeEach
    public void init() {
        var fakeSweetRepository = new FakeSweetRepository();
        sweetController = new SweetController(fakeSweetRepository);
    }

    @Test
    public void shouldReturnResponseWithStatus201() {
        var fakeSweet = fakeSweetDTO.createValidSweetDTO();
        var response = sweetController.create(fakeSweet);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    }

    @Test
    public void shouldReturnResponseWithStatus400() {
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
}
