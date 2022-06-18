package fr.sweetiez.api.adapter.shared;


import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetEntity;

import java.util.List;

public class SweetMapper {

    private final IngredientMapper ingredientMapper;
    private final EvaluationMapper evaluationMapper;

    public SweetMapper(IngredientMapper ingredientMapper, EvaluationMapper evaluationMapper) {
        this.ingredientMapper = ingredientMapper;
        this.evaluationMapper = evaluationMapper;
    }

    public SweetEntity toEntity(Sweet sweet) {
        return new SweetEntity(
                sweet.id().value(),
                sweet.name().value(),
                sweet.description().content(),
                sweet.price().unitPrice(),
                sweet.price().unitPerPackage(),
                sweet.details().characteristics().highlight(),
                sweet.details().characteristics().state(),
                sweet.details().characteristics().flavor(),
                sweet.details().images()
                        .stream()
                        .map(image -> image.isEmpty() ? image : image.concat(";"))
                        .reduce("", String::concat),
                sweet.ingredients().stream().map(ingredientMapper::toEntity).toList(),
                sweet.details().valuation().evaluations().stream().map(evaluationMapper::toEntity).toList()
        );
    }

    public Sweet toDto(SweetEntity entity) {
        return new Sweet(
                new ProductID(entity.getId()),
                new Name(entity.getName()),
                new Description(entity.getDescription()),
                new Price(entity.getPrice(), entity.getUnitPerPackage()),
                new Details(
                        List.of(entity.getImages().split(";")),
                        new Characteristics(entity.getHighlight(), entity.getState(), entity.getFlavor()),
                        new Valuation(entity.getEvaluations().stream().map(evaluationMapper::toDto).toList())
                ),
                entity.getIngredients().stream().map(ingredientMapper::toDto).toList()
        );
    }
}
