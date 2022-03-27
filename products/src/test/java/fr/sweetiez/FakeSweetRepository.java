package fr.sweetiez;

import fr.sweetiez.sweets.model.Sweet;
import fr.sweetiez.sweets.model.Sweets;

import java.util.HashSet;
import java.util.Set;

public class FakeSweetRepository implements Sweets {
    private final Set<Sweet> sweets;

    public FakeSweetRepository() {
        this.sweets = new HashSet<>();
    }

    public void save(Sweet sweet) {
        sweets.add(sweet);
    }

    public Set<Sweet> all() {
        return sweets;
    }
}
