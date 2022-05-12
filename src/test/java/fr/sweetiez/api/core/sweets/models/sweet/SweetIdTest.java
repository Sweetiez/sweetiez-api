package fr.sweetiez.api.core.sweets.models.sweet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SweetIdTest {

    SweetId sut;

    @Test
    public void shouldReturnTrueWhenValidateId() {
        sut = new SweetId("8985998c-2f05-4584-959c-2e5ccf50f980");
        assertTrue(sut.isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "8985998c-2f05-4584-959c-2e5ccf50f980c", "8985998c-2f054584-959c-2e5ccf50f98"})
    public void shouldReturnFalseWhenValidateId(String id) {
        sut = new SweetId(id);
        assertFalse(sut.isValid());
    }
}