package fr.sweetiez.api.sweet;


import java.util.Set;

public interface SweetRepository {
    void add(Sweet sweet);
    Set<Sweet> all();
}
