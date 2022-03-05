package fr.sweetiez.api.customer.adapters;

import fr.sweetiez.api.sweet.Sweet;
import fr.sweetiez.api.sweet.SweetRepository;

import java.util.LinkedHashSet;
import java.util.Set;

public class InMemorySweetRepository implements SweetRepository {
    private Set<Sweet> sweets = new LinkedHashSet<>();

    public void add(Sweet sweet) {
        sweets.add(sweet);
    }

    public Set<Sweet> all() {
        return null;
    }
}
