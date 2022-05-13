package fr.sweetiez.api.infrastructure.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.models.sweet.details.Flavor;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.infrastructure.repository.SweetEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import java.util.Set;
import java.util.UUID;
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
    private SweetEndPoints sweetsEndPoints;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void shouldCreateNewSweet() throws Exception {
        var sweetId = UUID.randomUUID().toString();
        var response = ResponseEntity.created(URI.create("/sweets/" + sweetId)).build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );

        when(sweetsEndPoints.create(any())).thenReturn(response);
        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/sweets/" + sweetId));

        verify(sweetsEndPoints).create(any());
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    @Test
    void shouldNotCreateNewSweetIfSweetNameAlreadyTaken() throws Exception {
        var response = ResponseEntity.status(HttpStatus.CONFLICT).build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );

        when(sweetsEndPoints.create(any())).thenReturn(response);
        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(sweetsEndPoints).create(any());
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    @Test
    void shouldNotCreateNewSweetIfFieldsAreInvalid() throws Exception {
        var response = ResponseEntity.badRequest().build();
        var requestBody = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );

        when(sweetsEndPoints.create(any())).thenReturn(response);

        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(sweetsEndPoints).create(any());
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    @ParameterizedTest
    @MethodSource("provideSetOfSweets")
    void shouldFindAllSweetWithPublishedState(Sweets publishedSweets) throws Exception {
        var jsonResponse = jsonMapper.writeValueAsString(publishedSweets);
        var response = ResponseEntity.ok(publishedSweets);
        when(sweetsEndPoints.retrievePublishedSweets()).thenReturn(response);

        mockMvc.perform(get("/sweets/published"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonResponse));

        verify(sweetsEndPoints).retrievePublishedSweets();
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    @Test
    void shouldNotPublishSweetIfSweetIdDoesNotExist() throws Exception {
        PublishSweetRequest body = new PublishSweetRequest(
                UUID.randomUUID().toString(),
                Highlight.PROMOTED,
                UUID.randomUUID().toString());

        when(sweetsEndPoints.publish(any())).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put("/sweets/publish")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(sweetsEndPoints).publish(any());
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    @Test
    void shouldChangeSweetStateToPublished() throws Exception {
        var sweetId = UUID.randomUUID();
        var createSweetRequest = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        var sweet = new Sweet(new SweetId(sweetId.toString()), createSweetRequest);
        var requestBody = new PublishSweetRequest(sweetId.toString(), Highlight.PROMOTED, UUID.randomUUID().toString());
        var response = ResponseEntity.ok(sweet);

        when(sweetsEndPoints.publish(any())).thenReturn(response);
        mockMvc.perform(put("/sweets/publish")
                        .content(jsonMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sweetsEndPoints).publish(any());
        verifyNoMoreInteractions(sweetsEndPoints);
    }

    public static Stream<Sweets> provideSetOfSweets() {
        var mapper = new SweetMapper();
        var entity = new SweetEntity(
                1L,
                UUID.randomUUID().toString(),
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