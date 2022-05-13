package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.details.Details;
import fr.sweetiez.api.core.sweets.models.sweet.details.Name;
import fr.sweetiez.api.core.sweets.models.sweet.details.Price;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;
import fr.sweetiez.api.infrastructure.repository.SweetEntity;

import java.util.List;
import java.util.Set;

public class SweetMapper {

    public SweetEntity toEntity(Sweet sweet) {
        return new SweetEntity(
                null,
                sweet.id().value(),
                sweet.name().value(),
                sweet.details().description(),
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
                new SweetId(entity.getId()),
                new Name(entity.getName()),
                new Price(entity.getPrice()),
                new States(entity.getHighlight(), entity.getState()),
                new Details(
                        entity.getDescription(),
                        entity.getFlavor(),
                        List.of(entity.getImages().split(";")),
                        new Ingredients(Set.of())
                )
        );
    }
}
