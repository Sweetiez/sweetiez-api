package fr.sweetiez.api.core.sweets.services;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.models.sweet.details.*;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetsReader reader;

    @Mock
    private SweetsWriter writer;

    @InjectMocks
    private SweetService sut;

    @Test
    void shouldCreateSweet() {
        var sweetId = new SweetId(UUID.randomUUID().toString());
        var request = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );

        when(reader.findAll()).thenReturn(new Sweets(Set.of()));
        when(writer.save(any())).thenReturn(new Sweet(sweetId, request));

        var sweet = sut.createSweet(request);

        assertEquals(State.CREATED, sweet.states().state());

        verify(reader).findAll();
        verifyNoMoreInteractions(reader);
        verify(writer).save(any());
        verifyNoMoreInteractions(writer);
    }

    @Test
    void shouldNotCreateSweetWhenSweetNameAlreadyExists() {
        var request = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        var id = new SweetId(UUID.randomUUID().toString());
        var sweet= new Sweet(id, request);

        when(reader.findAll()).thenReturn(new Sweets(Set.of(sweet)));

        assertThrowsExactly(
                SweetAlreadyExistsException.class,
                () -> sut.createSweet(request));

        verify(reader).findAll();
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateSweetRequest")
    void shouldNotCreateSweetWhenRequiredFieldsAreInvalid(CreateSweetRequest request) {

        when(reader.findAll()).thenReturn(new Sweets(Set.of()));

        assertThrowsExactly(
                InvalidFieldsException.class,
                () -> sut.createSweet(request));

        verify(reader).findAll();
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldPublishSweetWhenSweetIdExists() {
        var createRequest = new CreateSweetRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                Set.of(new Ingredient("Ingredient name", Set.of())),
                "Sweet description",
                Flavor.SWEET
        );
        var sweetId = new SweetId(UUID.randomUUID().toString());
        var sweet = new Sweet(sweetId, createRequest);
        var publishRequest = new PublishSweetRequest(sweetId.value(), Highlight.PROMOTED);

        when(reader.findById(any())).thenReturn(Optional.of(sweet));
        when(writer.save(any())).thenReturn(sweet);

        sut.publishSweet(publishRequest);
        var sweetArgumentCaptor = ArgumentCaptor.forClass(Sweet.class);

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verify(writer).save(sweetArgumentCaptor.capture());
        verifyNoMoreInteractions(writer);

        var capturedSweet = sweetArgumentCaptor.getValue();

        assertEquals(State.PUBLISHED, capturedSweet.states().state());
    }

    @Test
    void shouldNotPublishSweetWhenSweetIdDoesNotExist() {
        var sweetId = new SweetId(UUID.randomUUID().toString());
        var request = new PublishSweetRequest(sweetId.value(), Highlight.PROMOTED);

        when(reader.findById(any())).thenReturn(Optional.empty());

        assertThrowsExactly(
                NoSuchElementException.class,
                () -> sut.publishSweet(request));

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldProvideAnEmptyCollectionOfPublishedSweets() {
        when(reader.findAllPublished()).thenReturn(new Sweets(Set.of()));

        var sweets = sut.retrievePublishedSweets();

        assertTrue(sweets.content().isEmpty());

        verify(reader).findAllPublished();
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldProvideNonEmptyCollectionOfPublishedSweets() {
        var sweetId = new SweetId(UUID.randomUUID().toString());
        var publishedSweet = new Sweet(
                sweetId,
                new Name("Valid name"),
                new Price(BigDecimal.ONE),
                new States(
                        Highlight.PROMOTED,
                        State.PUBLISHED),
                new Details(
                        new Description(""),
                        Flavor.SWEET,
                        Set.of(),
                        new Ingredients(Set.of()),
                        5.)
        );

        when(reader.findAllPublished()).thenReturn(new Sweets(Set.of(publishedSweet)));

        var sweets = sut.retrievePublishedSweets();

        assertFalse(sweets.content().isEmpty());

        verify(reader).findAllPublished();
        verifyNoMoreInteractions(reader);
    }


    public static Stream<CreateSweetRequest> provideInvalidCreateSweetRequest() {
        return Stream.of(
                new CreateSweetRequest(
                        "sweet name",
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "",
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        null,
                        BigDecimal.valueOf(1.99),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(1.99),
                        null,
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(-1.45),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateSweetRequest(
                        "Sweet name",
                        BigDecimal.valueOf(0.),
                        Set.of(new Ingredient("Ingredient name", Set.of())),
                        "Sweet description",
                        Flavor.SWEET
                )
        );
    }
}