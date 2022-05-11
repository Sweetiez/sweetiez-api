package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.employees.models.EmployeeId;
import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.details.Details;
import fr.sweetiez.api.core.sweets.models.sweet.details.Name;
import fr.sweetiez.api.core.sweets.models.sweet.details.Price;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;
import fr.sweetiez.api.infrastructure.repository.SweetEntity;

import java.util.Set;

public class SweetMapper {

    public SweetEntity toEntity(Sweet sweet, EmployeeId employeeId) {
        return SweetEntity.builder()
                .id(sweet.id().value())
                .name(sweet.name().value())
                .price(sweet.price().value())
                .imageUrl(sweet.details().imageUrl())
                .description(sweet.details().description())
                .flavor(sweet.details().flavor())
                .state(sweet.states().state())
                .highlight(sweet.states().highlight())
                .creator(employeeId.value())
                .updateAuthor(employeeId.value())
                .build();
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
                        entity.getImageUrl(),
                        new Ingredients(Set.of())
                )
        );
    }
}
