package fr.sweetiez.sweets.domain;

import java.util.Set;

public interface Sweets {
    void save(Sweet sweet);
    Set<Sweet> all();
}
