package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.model.Sweet;
import fr.sweetiez.sweets.model.Sweets;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SweetRepository implements Sweets {

    public void save(Sweet sweet) {

    }

    public Set<Sweet> all() {
        return null;
    }
}
