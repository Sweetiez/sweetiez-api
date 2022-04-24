package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;
import fr.sweetiez.sweets.use_cases.PublishSweet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class SweetRepository implements Sweets {

    public void save(Sweet sweet) {

    }

    public Set<Sweet> all() {
        var sweets = new HashSet<Sweet>();
        var dto = new CreateSweetRequest(
                "So good",
                new HashSet<>(List.of("a", "b", "c")),
                BigDecimal.valueOf(0.95));
        var sweet = new Sweet(dto, sweets);
        sweets.add(sweet);

        return sweets;
    }

    public Optional<Sweet> findByID(String id) {
        return Optional.empty();
    }
}
