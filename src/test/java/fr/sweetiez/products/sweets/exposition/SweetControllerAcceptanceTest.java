package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.Flavor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SweetControllerAcceptanceTest {

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        url = "http://localhost:" + randomServerPort + "/sweets";
    }

    @Test
    void shouldCreateNewSweet() {
        var body = new CreateSweetRequest(
                "Sweet Name",
                Set.of("a", "b", "c"),
                "Sweet description",
                Flavor.SWEET,
                BigDecimal.valueOf(1.99),
                UUID.randomUUID()
        );

        ResponseEntity<Object> responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(url, body, Object.class);
        }
        catch (HttpStatusCodeException exception) {
            responseEntity = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }

        assertEquals(CREATED, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotCreateNewSweetIfSweetNameAlreadyExists() {
        var body = new CreateSweetRequest(
                "Sweet Name",
                Set.of("a", "b", "c"),
                "Sweet description",
                Flavor.SWEET,
                BigDecimal.valueOf(1.99),
                UUID.randomUUID()
        );

        ResponseEntity<Object> responseEntity;

        try {
            restTemplate.postForEntity(url, body, Object.class);
            responseEntity = restTemplate.postForEntity(url, body, Object.class);
        }
        catch(HttpStatusCodeException exception) {
            responseEntity = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }

        assertEquals(CONFLICT, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateSweetRequestBody")
    void shouldNotCreateNewSweetIfFieldsAreInvalid(CreateSweetRequest body) {
        ResponseEntity<Object> responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(url, body, Object.class);
        }
        catch(HttpStatusCodeException exception) {
            responseEntity = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }

        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    public static Stream<CreateSweetRequest> provideInvalidCreateSweetRequestBody() {
        return Stream.of(
                new CreateSweetRequest(
                        "sweet name",
                        Set.of("a", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "",
                        Set.of("a", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        null,
                        Set.of("a", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        null,
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        Set.of(),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        Set.of("", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(1.99),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        Set.of("a", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(-1.45),
                        UUID.randomUUID()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        Set.of("a", "b", "c"),
                        "Sweet description",
                        Flavor.SWEET,
                        BigDecimal.valueOf(0.),
                        UUID.randomUUID()
                )
        );
    }
}
