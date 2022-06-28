package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.products.models.SweetWithQuantity;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.infrastructure.repository.products.trays.SweetWithQuantityEntity;
import fr.sweetiez.api.infrastructure.repository.products.trays.TrayEntity;

import java.util.List;

public class TrayMapper {

    private final SweetMapper sweetMapper;
    private final EvaluationMapper evaluationMapper;

    public TrayMapper(SweetMapper sweetMapper, EvaluationMapper evaluationMapper) {
        this.sweetMapper = sweetMapper;
        this.evaluationMapper = evaluationMapper;
    }

    public TrayEntity toEntity(Tray tray) {
        var sweets = tray.sweets()
                .stream()
                .map(sweetQty -> new SweetWithQuantityEntity(
                        null,
                        sweetMapper.toEntity(sweetQty.sweet()),
                        sweetQty.quantity()))
                .toList();

        return new TrayEntity(
                tray.id().value(),
                tray.name().value(),
                tray.description().content(),
                tray.price().unitPrice(),
                tray.details().characteristics().highlight(),
                tray.details().characteristics().state(),
                tray.details().characteristics().flavor(),
                tray.details().images()
                        .stream()
                        .map(image -> image.isEmpty() ? image : image.concat(";"))
                        .reduce("", String::concat),
                sweets,
                tray.details().valuation().evaluations().stream().map(evaluationMapper::toEntity).toList()
        );
    }

    public Tray toDto(TrayEntity entity) {
        var sweets = entity.getSweets()
                .stream()
                .map(sweetQty -> new SweetWithQuantity(sweetQty.id(), sweetMapper.toDto(sweetQty.sweet()), sweetQty.quantity()))
                .toList();

        return new Tray(
                new ProductID(entity.getId()),
                new fr.sweetiez.api.core.products.models.common.Name(entity.getName()),
                new fr.sweetiez.api.core.products.models.common.Description(entity.getDescription()),
                new fr.sweetiez.api.core.products.models.common.Price(entity.getPrice()),
                new fr.sweetiez.api.core.products.models.common.details.Details(
                        List.of(entity.getImages().split(";")),
                        new Characteristics(entity.getHighlight(), entity.getState(), entity.getFlavor()),
                        new Valuation(entity.getEvaluations().stream().map(evaluationMapper::toDto).toList())
                ),
                sweets
        );
    }
}
