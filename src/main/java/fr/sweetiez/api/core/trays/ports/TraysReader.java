package fr.sweetiez.api.core.trays.ports;

import fr.sweetiez.api.core.trays.models.tray.Tray;
import fr.sweetiez.api.core.trays.models.tray.TrayId;
import fr.sweetiez.api.core.trays.models.tray.Trays;

import java.util.Optional;

public interface TraysReader {
    Optional<Tray> findById(TrayId id);
    Trays findAllPublished();
    Trays findAll();
}
