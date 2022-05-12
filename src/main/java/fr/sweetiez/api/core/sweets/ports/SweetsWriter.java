package fr.sweetiez.api.core.sweets.ports;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;

public interface SweetsWriter {
    Sweet save(Sweet sweet);
}
