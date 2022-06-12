package fr.sweetiez.api.core.sweets.services;

import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
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
import fr.sweetiez.api.core.products.models.requests.CreateProductRequest;
import fr.sweetiez.api.core.products.models.requests.PublishProductRequest;
import fr.sweetiez.api.core.products.models.requests.UnpublishProductRequest;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.core.products.ports.ProductsWriter;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.products.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.products.services.exceptions.ProductAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private ProductsReader<Sweet> reader;

    @Mock
    private ProductsWriter<Sweet> writer;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private SweetService sut;

    @Test
    void shouldCreateSweet() {
        var request = new CreateProductRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(UUID.randomUUID()),
                "Sweet description",
                Flavor.SWEET
        );

        when(ingredientService.retrieveAllById(any())).
                thenReturn(List.of(new Ingredient(UUID.randomUUID(), "Banana", List.of())));
        when(reader.findAll()).thenReturn(List.of());
        when(writer.save(any())).thenReturn(new Sweet(request, List.of()));

        var sweet = sut.create(request);

        assertEquals(State.CREATED, sweet.details().characteristics().state());

        verify(reader).findAll();
        verifyNoMoreInteractions(reader);
        verify(writer).save(any());
        verifyNoMoreInteractions(writer);
    }

    @Test
    void shouldNotCreateSweetWhenSweetNameAlreadyExists() {
        var ingredientId = UUID.randomUUID();
        var request = new CreateProductRequest(
                "Sweet name",
                BigDecimal.valueOf(1.99),
                List.of(ingredientId),
                "Sweet description",
                Flavor.SWEET
        );

        var ingredient = new Ingredient(ingredientId, "Banana", List.of());
        var sweet = new Sweet(request, List.of(ingredient));

        when(ingredientService.retrieveAllById(request.composition())).thenReturn(List.of(ingredient));
        when(reader.findAll()).thenReturn(List.of(sweet));

        assertThrowsExactly(ProductAlreadyExistsException.class, () -> sut.create(request));

        verify(reader).findAll();
        verify(ingredientService).retrieveAllById(request.composition());
        verifyNoMoreInteractions(reader, ingredientService);
        verifyNoInteractions(writer);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateSweetRequest")
    void shouldNotCreateSweetWhenRequiredFieldsAreInvalid(CreateProductRequest request) {

        when(ingredientService.retrieveAllById(request.composition())).thenReturn(List.of());

        assertThrowsExactly(InvalidFieldsException.class, () -> sut.create(request));

        verify(ingredientService).retrieveAllById(request.composition());
        verifyNoMoreInteractions(ingredientService);
        verifyNoInteractions(reader, writer);
    }

    @Test
    void shouldPublishSweetWhenSweetIdExists() {
        var ingredient = new Ingredient(UUID.randomUUID(), "Banana", List.of());
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
                List.of(ingredient)
        );

        var publishRequest = new PublishProductRequest(sweet.id().value(), Highlight.PROMOTED);

        when(reader.findById(any())).thenReturn(Optional.of(sweet));
        when(writer.save(any())).thenReturn(sweet);

        sut.publish(publishRequest);
        var sweetArgumentCaptor = ArgumentCaptor.forClass(Sweet.class);

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verify(writer).save(sweetArgumentCaptor.capture());
        verifyNoMoreInteractions(writer);

        var capturedSweet = sweetArgumentCaptor.getValue();

        assertEquals(State.PUBLISHED, capturedSweet.details().characteristics().state());
    }

    @Test
    void shouldNotPublishSweetWhenSweetIdDoesNotExist() {
        var sweetId = new ProductID(UUID.randomUUID());
        var request = new PublishProductRequest(sweetId.value(), Highlight.PROMOTED);

        when(reader.findById(any())).thenReturn(Optional.empty());

        assertThrowsExactly(
                NoSuchElementException.class,
                () -> sut.publish(request));

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldUnpublishSweetWhenSweetIdExists() {
        var ingredient = new Ingredient(UUID.randomUUID(), "Banana", List.of());
        var sweet = new Sweet(
                new ProductID(UUID.randomUUID()),
                new Name("Sweet name"),
                new Description("Sweet description"),
                new Price(BigDecimal.valueOf(1.99)),
                new Details(
                        List.of(),
                        new Characteristics(Highlight.BANNER, State.PUBLISHED, Flavor.SWEET),
                        new Valuation(List.of())
                ),
                List.of(ingredient)
        );

        var publishRequest = new UnpublishProductRequest(sweet.id().value());

        when(reader.findById(any())).thenReturn(Optional.of(sweet));
        when(writer.save(any())).thenReturn(sweet);

        sut.unpublish(publishRequest);
        var sweetArgumentCaptor = ArgumentCaptor.forClass(Sweet.class);

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verify(writer).save(sweetArgumentCaptor.capture());
        verifyNoMoreInteractions(writer);

        var capturedSweet = sweetArgumentCaptor.getValue();

        assertEquals(State.NON_PUBLISHED, capturedSweet.details().characteristics().state());
    }

    @Test
    void shouldNotUnPublishSweetWhenSweetIdDoesNotExist() {
        var sweetId = new ProductID(UUID.randomUUID());
        var request = new UnpublishProductRequest(sweetId.value());

        when(reader.findById(any())).thenReturn(Optional.empty());

        assertThrowsExactly(
                NoSuchElementException.class,
                () -> sut.unpublish(request));

        verify(reader).findById(any());
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldProvideAnEmptyCollectionOfPublishedSweets() {
        when(reader.findAllPublished()).thenReturn(List.of());

        var sweets = sut.retrieveAllPublished();

        assertTrue(sweets.isEmpty());

        verify(reader).findAllPublished();
        verifyNoMoreInteractions(reader);
        verifyNoInteractions(writer);
    }

    @Test
    void shouldProvideNonEmptyCollectionOfPublishedSweets() {
        var sweetId = new ProductID(UUID.randomUUID());
        var publishedSweet = new Sweet(
                sweetId,
                new Name("Valid name"),
                new Description(""),
                new Price(BigDecimal.ONE),
                new Details(
                        List.of(),
                        new Characteristics(Highlight.COMMON, State.PUBLISHED, Flavor.SWEET),
                        new Valuation(List.of())
                ),
                List.of()
        );

        when(reader.findAllPublished()).thenReturn(List.of(publishedSweet));

        var sweets = sut.retrieveAllPublished();

        assertFalse(sweets.isEmpty());

        verify(reader).findAllPublished();
        verifyNoMoreInteractions(reader);
    }


    public static Stream<CreateProductRequest> provideInvalidCreateSweetRequest() {
        return Stream.of(
                new CreateProductRequest(
                        "sweet name",
                        BigDecimal.valueOf(1.99),
                        List.of(UUID.randomUUID()),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateProductRequest(
                        "",
                        BigDecimal.valueOf(1.99),
                        List.of(UUID.randomUUID()),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateProductRequest(
                        null,
                        BigDecimal.valueOf(1.99),
                        List.of(UUID.randomUUID()),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateProductRequest(
                        "Sweet name",
                        BigDecimal.valueOf(1.99),
                        null,
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateProductRequest(
                        "Sweet name",
                        BigDecimal.valueOf(-1.45),
                        List.of(UUID.randomUUID()),
                        "Sweet description",
                        Flavor.SWEET
                ),
                new CreateProductRequest(
                        "Sweet name",
                        BigDecimal.valueOf(0.),
                        List.of(UUID.randomUUID()),
                        "Sweet description",
                        Flavor.SWEET
                )
        );
    }
}