package fr.sweetiez.sweets.model;

import java.util.Set;

public interface Sweets {
    void save(Sweet sweet);
    Set<Sweet> all();
}
