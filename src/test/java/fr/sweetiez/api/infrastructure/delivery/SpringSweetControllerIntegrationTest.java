package fr.sweetiez.api.infrastructure.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.adapter.delivery.sweet.AdminSweetEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.SimpleSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.infrastructure.delivery.sweet.SpringSweetController;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetEntity;
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
import java.util.Set;
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
                Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
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
                Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
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
                Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
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
    @MethodSource("provideSetOfSweets")
    void shouldFindAllSweetWithPublishedState(Sweets publishedSweets) throws Exception {
        Collection<SimpleSweetResponse> responseBody = publishedSweets.content()
                .stream()
                .map(SimpleSweetResponse::new)
                .collect(Collectors.toSet());
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
        PublishSweetRequest body = new PublishSweetRequest(
                UUID.randomUUID().toString(),
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
        var createSweetRequest = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient(UUID.randomUUID(), "Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        var sweet = new Sweet(new SweetId(sweetId.toString()), createSweetRequest);
        var requestBody = new PublishSweetRequest(sweetId.toString(), Highlight.PROMOTED);
        var response = ResponseEntity.ok(sweet);

        when(adminSweetEndPoints.publish(any())).thenReturn(response);
        mockMvc.perform(put("/admin/sweets/publish")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adminSweetEndPoints).publish(any());
        verifyNoMoreInteractions(adminSweetEndPoints);
    }

    public static Stream<Sweets> provideSetOfSweets() {
        var mapper = new SweetMapper();
        var entity = new SweetEntity(
                UUID.randomUUID(),
                "Sweet name",
                "",
                BigDecimal.ONE,
                Highlight.COMMON,
                State.PUBLISHED,
                Flavor.SWEET,
                ""
        );

        return Stream.of(
                new Sweets(Set.of()),
                new Sweets(Set.of(mapper.toDto(entity)))
        );
    }

}