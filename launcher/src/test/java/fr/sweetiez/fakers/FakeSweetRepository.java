package fr.sweetiez.fakers;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.SweetID;
import fr.sweetiez.sweets.domain.Sweets;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FakeSweetRepository implements Sweets {
    private final Set<Sweet> sweets;

    public FakeSweetRepository() {
        this.sweets = new HashSet<>();
    }

    public Optional<UUID> save(Sweet sweet, UUID creator) {
        sweets.add(sweet);
        return Optional.of(sweet.getId().getId());
    }

    public Set<Sweet> all() {
        return sweets;
    }

    public Optional<Sweet> findByID(String id) {
        SweetID sweetID = new SweetID(UUID.fromString(id));
        return sweets.stream()
                .filter(sweet -> sweet.getId().equals(sweetID))
                .findFirst();
    }

    @Override
    public void update(Sweet publishedSweet, UUID randomUUID) {

    }
}
