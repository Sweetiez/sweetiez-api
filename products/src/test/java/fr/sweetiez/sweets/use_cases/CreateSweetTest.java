package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.FakeSweetRepository;
import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.Status;
import fr.sweetiez.sweets.domain.exceptions.InvalidIngredientsException;
import fr.sweetiez.sweets.domain.exceptions.InvalidPriceException;
import fr.sweetiez.sweets.domain.exceptions.InvalidSweetNameException;
import fr.sweetiez.sweets.domain.exceptions.SweetAlreadyExistsException;
import fr.sweetiez.sweets.fakers.FakeSweetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateSweetTest {
    private final FakeSweetDTO fakeSweetDTO;
    private CreateSweet admin;

    private CreateSweetTest() {
        fakeSweetDTO = new FakeSweetDTO();
    }

    @BeforeEach
    public void init() {
        var fakeSweetRepository = new FakeSweetRepository();
        admin = new CreateSweet(fakeSweetRepository);
    }

    @Test
    public void shouldCreateANewSweet() {
        var sweetDto = fakeSweetDTO.createValidSweetDTO();
        assertThatCode(() -> admin.create(sweetDto)).doesNotThrowAnyException();
    }

    @Test
    public void shouldHaveCreatedStatusAndCommonPriority() {
        var sweetDto = fakeSweetDTO.createValidSweetDTO();
        var sweet = admin.create(sweetDto);

        assertEquals(Status.CREATED, sweet.getStatus());
        assertEquals(Priority.COMMON, sweet.getPriority());
    }

    @Test void shouldThrowAnExceptionIfSweetNameIsAlreadyTaken() {
        var sweetDto = fakeSweetDTO.createValidSweetDTO();
        assertThatCode(() -> admin.create(sweetDto)).doesNotThrowAnyException();

        ThrowingCallable creatSweet = () -> admin.create(fakeSweetDTO.copyOf(sweetDto));
        assertThatExceptionOfType(SweetAlreadyExistsException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetNameIsEmpty() {
        var sweetDto = fakeSweetDTO.withEmptyName();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidSweetNameException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetNameIsNull() {
        var sweetDto = fakeSweetDTO.withNameEqualsNull();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidSweetNameException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetPriceIsNull() {
        var sweetDto = fakeSweetDTO.withPriceEqualsNull();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidPriceException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetNameContainsNumbers() {
        var sweetDto = fakeSweetDTO.withInvalidName();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidSweetNameException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetPriceIsLowerThanZero() {
        var sweetDto = fakeSweetDTO.withNegativePrice();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidPriceException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetPriceEqualsToZero() {
        var sweetDto = fakeSweetDTO.withPriceEqualsZero();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidPriceException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetIngredientsIsNull() {
        var sweetDto = fakeSweetDTO.withIngredientsEqualsNull();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidIngredientsException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetIngredientsIsEmpty() {
        var sweetDto = fakeSweetDTO.withEmptyIngredients();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidIngredientsException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetIngredientsContainsNullValue() {
        var sweetDto = fakeSweetDTO.withIngredientsContainingNullValue();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidIngredientsException.class).isThrownBy(creatSweet);
    }

    @Test
    public void shouldThrowAnExceptionIfSweetIngredientsContainsEmptyValue() {
        var sweetDto = fakeSweetDTO.withIngredientsContainingEmptyValue();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidIngredientsException.class).isThrownBy(creatSweet);
    }
}
