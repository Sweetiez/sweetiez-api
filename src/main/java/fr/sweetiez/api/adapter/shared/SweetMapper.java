package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.details.Description;
import fr.sweetiez.api.core.sweets.models.sweet.details.Details;
import fr.sweetiez.api.core.sweets.models.sweet.details.Name;
import fr.sweetiez.api.core.sweets.models.sweet.details.Price;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SweetMapper {

    public SweetEntity toEntity(Sweet sweet) {
        return new SweetEntity(
                UUID.fromString(sweet.id().value()),
                sweet.name().value(),
                sweet.details().description().content(),
                sweet.price().value(),
                sweet.states().highlight(),
                sweet.states().state(),
                sweet.details().flavor(),
                sweet.details().images()
                        .stream()
                        .map(image -> image.concat(";"))
                        .reduce("", String::concat)
        );
    }

    public Sweet toDto(SweetEntity entity) {
        return new Sweet(
                new SweetId(entity.getId().toString()),
                new Name(entity.getName()),
                new Price(entity.getPrice()),
                new States(entity.getHighlight(), entity.getState()),
                new Details(
                        new Description(entity.getDescription()),
                        entity.getFlavor(),
                        List.of(entity.getImages().split(";")),
                        new Ingredients(Set.of()),
                        5.
                )
        );
    }
}
