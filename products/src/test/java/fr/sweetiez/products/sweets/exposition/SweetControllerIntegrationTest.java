package fr.sweetiez.products.sweets.exposition;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.products.common.Flavor;
import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.domain.Sweet;
import fr.sweetiez.products.sweets.domain.Sweets;
import fr.sweetiez.products.sweets.domain.exceptions.InvalidFieldsException;
import fr.sweetiez.products.sweets.domain.exceptions.SweetAlreadyExistsException;
import fr.sweetiez.products.sweets.infra.SweetEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SweetControllerIntegrationTest {

    @MockBean
    private Sweets service;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void shouldCreateNewSweet() throws Exception {
        UUID sweetId = UUID.randomUUID();
        Sweet sweet = new Sweet.Builder().id(sweetId).build();
        CreateSweetRequest body = new CreateSweetRequest(
                "Sweet name",
                Set.of("a", "b", "c"),
                "Sweet description",
                Flavor.SWEET,
                BigDecimal.valueOf(1.99),
                UUID.randomUUID()
        );

        when(service.create(any(), any())).thenReturn(sweet);

        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/sweets/" + sweetId));

        verify(service).create(any(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotCreateNewSweetIfSweetNameAlreadyTaken() throws Exception {
        CreateSweetRequest body = new CreateSweetRequest(
                "Sweet name",
                Set.of("a", "b", "c"),
                "Sweet description",
                Flavor.SWEET,
                BigDecimal.valueOf(1.99),
                UUID.randomUUID()
        );

        when(service.create(any(), any())).thenThrow(SweetAlreadyExistsException.class);

        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(service).create(any(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotCreateNewSweetIfFieldsAreInvalid() throws Exception {
        CreateSweetRequest body = new CreateSweetRequest(
                "Sweet name",
                Set.of("a", "b", "c"),
                "Sweet description",
                Flavor.SWEET,
                BigDecimal.valueOf(1.99),
                UUID.randomUUID()
        );

        when(service.create(any(), any())).thenThrow(InvalidFieldsException.class);

        mockMvc.perform(post("/sweets")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service).create(any(), any());
        verifyNoMoreInteractions(service);
    }

    @ParameterizedTest
    @MethodSource("provideSetOfSweets")
    void shouldFindAllSweetWithPublishedState(Set<Sweet> publishedSweets) throws Exception {
        String jsonResponse = jsonMapper.writeValueAsString(publishedSweets);
        when(service.findAllPublished()).thenReturn(publishedSweets);

        mockMvc.perform(get("/sweets/published"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonResponse));

        verify(service).findAllPublished();
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldNotPublishSweetIfSweetIdDoesNotExist() throws Exception {
        PublishSweetRequest body = new PublishSweetRequest(UUID.randomUUID(), Highlight.PROMOTED, UUID.randomUUID());
        when(service.publish(anyString(), any(), any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put("/sweets/publish")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service).publish(anyString(), any(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldChangeSweetStateToPublished() throws Exception {
        UUID sweetId = UUID.randomUUID();
        Sweet sweet = new Sweet.Builder().id(sweetId).build();
        PublishSweetRequest body = new PublishSweetRequest(UUID.randomUUID(), Highlight.PROMOTED, UUID.randomUUID());

        when(service.publish(anyString(), any(), any())).thenReturn(sweet);

        mockMvc.perform(put("/sweets/publish")
                        .content(jsonMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).publish(anyString(), any(), any());
        verifyNoMoreInteractions(service);
    }

    public static Stream<Set<Sweet>> provideSetOfSweets() {
        SweetEntity entity = SweetEntity.builder()
                .dbId(1L)
                .id(UUID.randomUUID().toString())
                .name("Sweet name")
                .state(State.PUBLISHED)
                .build();

        return Stream.of(
                Set.of(),
                Set.of(entity.toSweet())
        );
    }
}