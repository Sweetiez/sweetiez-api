package fr.sweetiez.fakers;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SweetFaker {

    public Sweet fakeSweet(String name) {
        var sweets = new HashSet<Sweet>();
        return new Sweet(
                new CreateSweetRequest(name, new HashSet<>(List.of("a", "b", "c")), new BigDecimal("0.95")),
                sweets);
    }

    public Set<Sweet> fakeSweetsSet() {
        var sweets = new HashSet<Sweet>();

        for (int i = 0; i < 3; i++) {
            var sweet = new Sweet(
                    new CreateSweetRequest("a" + i, new HashSet<>(List.of("a", "b", "c")), new BigDecimal(i + 0.95)),
                    sweets);
            sweets.add(sweet);
        }

        return sweets;
    }
}
