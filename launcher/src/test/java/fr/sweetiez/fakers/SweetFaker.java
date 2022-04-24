package fr.sweetiez.fakers;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.SweetType;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SweetFaker {

    public Sweet fakeSweet(String name) {
        var sweets = new HashSet<Sweet>();
        return new Sweet(
                new CreateSweetRequest(name, new HashSet<>(List.of("a", "b", "c")), "description", new BigDecimal("0.95"), SweetType.SALTY, UUID.randomUUID()),
                sweets);
    }

    public Set<Sweet> fakeSweetsSet() {
        var sweets = new HashSet<Sweet>();

        for (int i = 0; i < 3; i++) {
            var sweet = new Sweet(
                    new CreateSweetRequest("a" + i, new HashSet<>(List.of("a", "b", "c")), "description", new BigDecimal(i + 0.95), SweetType.SALTY, UUID.randomUUID()),
                    sweets);
            sweets.add(sweet);
        }

        return sweets;
    }
}
