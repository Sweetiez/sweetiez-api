package fr.sweetiez.api.core.sweets.ports;

import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;

import java.util.Optional;

public interface SweetsReader {
    Optional<Sweet> findById(SweetId id);
    Sweets findAllPublished();
    Sweets findAll();
}
