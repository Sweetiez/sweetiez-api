package fr.sweetiez.api.acceptance.steps;

import io.cucumber.java8.En;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps implements En {

    public CartSteps() {
        And("^the cart value is \"([^\"]*)\" with \"([^\"]*)\" items remaining$", (BigDecimal price, Long quantity) -> {
            assertTrue(price.compareTo(BigDecimal.valueOf(25)) > 0);
            assertTrue(quantity >= 30);
        });
    }
}
