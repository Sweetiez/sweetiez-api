package fr.sweetiez.api.infrastructure.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.adapter.delivery.sweet.AdminSweetEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
import fr.sweetiez.api.adapter.shared.*;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Flavor;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.core.products.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.products.models.requests.PublishProductRequest;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.infrastructure.delivery.sweet.SpringSweetController;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = SpringSweetController.class)
@Disabled
class SpringSweetControllerIntegrationTest {

    @MockBean
    private AdminSweetEndPoints adminSweetEndPoints;

    @MockBean
    private SweetEndPoints sweetEndPoints;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    @Disabled
    void shouldCreateNewSweet() throws Exception {
        var sweetId = UUID.randomUUID().toString();
        var response = ResponseEntity.created(URI.create("/sweets/" + sweetId)).build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(UUID.randomUUID()),
                "Sweet description",
                Flavor.SWEET
        );

        when(adminSweetEndPoints.create(any())).thenReturn(response);
        mockMvc.perform(post("/admin/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/sweets/" + sweetId));

        verify(adminSweetEndPoints).create(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    @Test
    @Disabled
    void shouldNotCreateNewSweetIfSweetNameAlreadyTaken() throws Exception {
        var response = ResponseEntity.status(HttpStatus.CONFLICT).build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(UUID.randomUUID()),
                "Sweet description",
                Flavor.SWEET
        );

        when(adminSweetEndPoints.create(any())).thenReturn(response);
        mockMvc.perform(post("/admin/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(adminSweetEndPoints).create(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    @Test
    @Disabled
    void shouldNotCreateNewSweetIfFieldsAreInvalid() throws Exception {
        var response = ResponseEntity.badRequest().build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(UUID.randomUUID()),
                "Sweet description",
                Flavor.SWEET
        );

        when(adminSweetEndPoints.create(any())).thenReturn(response);

        mockMvc.perform(post("/admin/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(adminSweetEndPoints).create(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    @ParameterizedTest
    @MethodSource("provideListOfSweets")
    void shouldFindAllSweetWithPublishedState(Collection<Sweet> publishedSweets) throws Exception {
        Collection<SimpleProductResponse> responseBody = publishedSweets
                .stream()
                .map(SimpleProductResponse::new)
                .collect(Collectors.toList());
        var response = ResponseEntity.ok(responseBody);
        var jsonResponse = jsonMapper.writeValueAsString(responseBody);

        when(sweetEndPoints.retrievePublishedSweets()).thenReturn(response);
        mockMvc.perform(get("/sweets/published"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonResponse));

        verify(sweetEndPoints).retrievePublishedSweets();
        verifyNoMoreInteractions(sweetEndPoints);
    }

    @Test
    @Disabled
    void shouldNotPublishSweetIfSweetIdDoesNotExist() throws Exception {
        var body = new PublishProductRequest(
                UUID.randomUUID(),
                Highlight.PROMOTED);

        when(adminSweetEndPoints.publish(any())).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put("/admin/sweets/publish")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(adminSweetEndPoints).publish(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    @Test
    @Disabled
    void shouldChangeSweetStateToPublished() throws Exception {
        var sweetId = UUID.randomUUID();
        var createProductRequest = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(UUID.randomUUID()),
                "Sweet description",
                Flavor.SWEET
        );
        var sweet = new Sweet(
                new ProductID(UUID.randomUUID()),
                new Name("Sweet name"),
                new Description("Sweet description"),
                new Price(BigDecimal.valueOf(1.99)),
                new Details(
                        List.of(),
                        new Characteristics(Highlight.COMMON, State.CREATED, Flavor.SWEET),
                        new Valuation(List.of())
                ),
                List.of());
        var requestBody = new PublishProductRequest(sweetId, Highlight.PROMOTED);
        var response = ResponseEntity.ok(new SimpleProductResponse(sweet));

        when(adminSweetEndPoints.publish(any())).thenReturn(response);
        mockMvc.perform(put("/admin/sweets/publish")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adminSweetEndPoints).publish(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    public static Stream<Collection<Sweet>> provideListOfSweets() {
        var mapper = new SweetMapper(new IngredientMapper(), new EvaluationMapper(new CustomerMapper(new AccountMapper())));
        var entity = new SweetEntity(
                UUID.randomUUID(),
                "Sweet name",
                "",
                BigDecimal.ONE,
                Highlight.COMMON,
                State.PUBLISHED,
                Flavor.SWEET,
                "",
                List.of(),
                List.of()
        );

        return Stream.of(
                List.of(),
                List.of(mapper.toDto(entity))
        );
    }

}