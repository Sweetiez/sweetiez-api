package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.trays.models.tray.Tray;
import fr.sweetiez.api.core.trays.models.tray.TrayId;
import fr.sweetiez.api.core.trays.models.tray.details.Description;
import fr.sweetiez.api.core.trays.models.tray.details.Details;
import fr.sweetiez.api.core.trays.models.tray.details.Name;
import fr.sweetiez.api.core.trays.models.tray.details.Price;
import fr.sweetiez.api.core.trays.models.tray.states.States;
import fr.sweetiez.api.infrastructure.repository.trays.TrayEntity;

import java.util.List;
import java.util.UUID;

public class TrayMapper {

    private final SweetMapper sweetMapper;

    public TrayMapper(SweetMapper sweetMapper) {
        this.sweetMapper = sweetMapper;
    }

    public TrayEntity toEntity(Tray tray) {
        return new TrayEntity(
                UUID.fromString(tray.id().value()),
                tray.name().value(),
                tray.details().description().content(),
                tray.price().value(),
                tray.states().highlight(),
                tray.states().state(),
                tray.details().flavor(),
                tray.details().images()
                        .stream()
                        .map(image -> image.isEmpty() ? image : image.concat(";"))
                        .reduce("", String::concat),
                tray.details().sweets().stream().map(sweetMapper::toEntity).toList()
        );
    }

    public Tray toDto(TrayEntity entity) {
        return new Tray(
                new TrayId(entity.getId().toString()),
                new Name(entity.getName()),
                new Price(entity.getPrice()),
                new States(entity.getHighlight(), entity.getState()),
                new Details(
                        new Description(entity.getDescription()),
                        entity.getFlavor(),
                        List.of(entity.getImages().split(";")),
                        entity.getSweets().stream().map(sweetMapper::toDto).toList(),
                        5.
                )
        );
    }
}
