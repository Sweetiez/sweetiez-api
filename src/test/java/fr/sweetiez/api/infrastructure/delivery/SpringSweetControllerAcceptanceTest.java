package fr.sweetiez.api.infrastructure.delivery;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.infrastructure.app.run.SpringRun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringRun.class
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpringSweetControllerAcceptanceTest {

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
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET,
                UUID.randomUUID().toString()
        );

        ResponseEntity<Object> responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(url, requestBody, Object.class);
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
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET,
                UUID.randomUUID().toString()
        );

        ResponseEntity<Object> responseEntity;

        try {
            restTemplate.postForEntity(url, requestBody, Object.class);
            responseEntity = restTemplate.postForEntity(url, requestBody, Object.class);
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
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                ),
                new CreateSweetRequest(
                        "",
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                ),
                new CreateSweetRequest(
                        null,
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(1.99),
                        null,
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                ),
//                new CreateSweetRequest(
//                        "Sweet name",
//                        BigDecimal.valueOf(1.99),
//                        Set.of(),
//                        "Sweet description",
//                        Flavor.SWEET,
//                        UUID.randomUUID().toString()
//                ),
//                new CreateSweetRequest(
//                        "Sweet name",
//                        BigDecimal.valueOf(1.99),
//                        Set.of(new Ingredient("", Set.of())),
//                        "Sweet description",
//                        Flavor.SWEET,
//                        UUID.randomUUID().toString()
//                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(-1.45),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(0.),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET,
                        UUID.randomUUID().toString()
                )
        );
    }
}