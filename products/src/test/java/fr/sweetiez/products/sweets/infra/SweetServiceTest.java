package fr.sweetiez.products.sweets.infra;

import fr.sweetiez.products.common.validators.FieldValidator;
import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.domain.Sweet;
import fr.sweetiez.products.sweets.domain.exceptions.InvalidFieldsException;
import fr.sweetiez.products.sweets.domain.exceptions.SweetAlreadyExistsException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository repository;

    @Mock
    private FieldValidator<Sweet> validator;

    @InjectMocks
    private SweetService sut;

    @Test
    void shouldNotCreateSweetWhenSweetNameAlreadyExists() {
        Sweet sweet = new Sweet.Builder()
                .name("Sweet name")
                .build();

        SweetEntity entity = SweetEntity.builder()
                .name(sweet.getName())
                .build();

        when(validator.hasErrors(any())).thenReturn(false);
        when(repository.existsByName(anyString())).thenReturn(true);
        when(repository.findAll()).thenReturn(List.of(entity));

        ThrowingCallable callable = () -> sut.create(sweet, UUID.randomUUID());
        assertThatThrownBy(callable).isInstanceOf(SweetAlreadyExistsException.class);

        verify(validator).hasErrors(any());
        verify(repository).existsByName(anyString());
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldNotCreateSweetWhenRequiredFieldsAreInvalid() {
        Sweet sweet = new Sweet.Builder()
                .name("Sweet name")
                .build();

        when(validator.hasErrors(any())).thenReturn(true);

        ThrowingCallable callable = () -> sut.create(sweet, UUID.randomUUID());
        assertThatThrownBy(callable).isInstanceOf(InvalidFieldsException.class);

        verify(validator).hasErrors(any());
        verifyNoInteractions(repository);
    }

    @Test
    void shouldCreateSweet() {
        Sweet sweet = new Sweet.Builder()
                .name("Sweet name")
                .build();

        when(validator.hasErrors(any())).thenReturn(false);
        when(repository.findAll()).thenReturn(List.of());
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(mock(SweetEntity.class));

        assertThatCode(() -> sut.create(sweet, UUID.randomUUID())).doesNotThrowAnyException();

        verify(validator).hasErrors(any());
        verify(repository).findAll();
        verify(repository).existsByName(anyString());
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldNotPublishSweetWhenSweetIdDoesNotExist() {
        UUID sweetId = UUID.randomUUID();

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        ThrowingCallable callable = () -> sut.publish(sweetId.toString(), Highlight.COMMON, UUID.randomUUID());
        assertThatThrownBy(callable).isInstanceOf(NoSuchElementException.class);

        verify(repository).findById(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldPublishSweetWhenSweetIdExists() {
        UUID sweetId = UUID.randomUUID();
        SweetEntity entity = SweetEntity.builder()
                .dbId(1L)
                .id(sweetId.toString())
                .name("Sweet name")
                .state(State.CREATED)
                .build();

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        sut.publish(sweetId.toString(), Highlight.COMMON, UUID.randomUUID());
        var sweetEntityArgumentCaptor = ArgumentCaptor.forClass(SweetEntity.class);

        verify(repository).findById(anyString());
        verify(repository).save(sweetEntityArgumentCaptor.capture());
        verifyNoMoreInteractions(repository);

        SweetEntity capturedEntity = sweetEntityArgumentCaptor.getValue();
        assertThat(entity.equals(capturedEntity)
                && capturedEntity.getState().equals(State.PUBLISHED))
                .isTrue();
    }

    @Test
    void shouldProvideAnEmptyCollectionOfSweets() {
        when(repository.findAll()).thenReturn(List.of());

        var res = sut.findAllPublished();

        assertTrue(res.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldProvideNonEmptyCollectionOfPublishedSweets() {
        SweetEntity entity = SweetEntity.builder()
                .dbId(1L)
                .id(UUID.randomUUID().toString())
                .name("Sweet name")
                .state(State.PUBLISHED)
                .build();

        when(repository.findAll()).thenReturn(List.of(entity));

        var res = sut.findAllPublished();

        assertFalse(res.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowAnErrorWhenIdOfSweetDoesNotExist() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        ThrowingCallable callable = () -> sut.findById(UUID.randomUUID());
        assertThatThrownBy(callable).isInstanceOf(NoSuchElementException.class);

        verify(repository).findById(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldFindSweetWhenIdExists() {
        UUID id = UUID.randomUUID();
        when(repository.findById(anyString()))
                .thenReturn(Optional.of(SweetEntity.builder().id(id.toString()).build()));

        Sweet sweet = sut.findById(id);

        assertEquals(id, sweet.getId());

        verify(repository).findById(anyString());
        verifyNoMoreInteractions(repository);
    }

}