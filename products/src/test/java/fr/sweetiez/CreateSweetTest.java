package fr.sweetiez;

import fr.sweetiez.sweets.use_cases.CreateSweet;
import fr.sweetiez.sweets.use_cases.InvalidSweetNameException;
import fr.sweetiez.sweets.use_cases.SweetAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.ThrowableAssert.*;

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
    public void shouldThrowAnExceptionIfSweetNameContainsNumbers() {
        var sweetDto = fakeSweetDTO.withNumbersInName();

        ThrowingCallable creatSweet = () -> admin.create(sweetDto);
        assertThatExceptionOfType(InvalidSweetNameException.class).isThrownBy(creatSweet);
    }
}
