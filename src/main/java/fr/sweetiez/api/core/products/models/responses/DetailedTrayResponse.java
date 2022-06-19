package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Tray;

import java.util.Collection;
import java.util.UUID;

public record DetailedTrayResponse(
        UUID id,
        String name,
        double price,
        String description,
        Collection<String> images,
        Collection<SimpleSweetWithQuantity> sweets,
        Collection<String> diets,
        Collection<String> allergens,
        ValuationResponse valuation
)
{
    public DetailedTrayResponse(Tray tray, ValuationResponse valuation) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().unitPrice().doubleValue(),
                tray.description().content(),
                tray.details().images(),
                tray.sweets().stream()
                        .map(sweetQty -> new SimpleSweetWithQuantity(
                                new SimpleProductResponse(
                                        sweetQty.sweet()),
                                sweetQty.quantity() * sweetQty.sweet().price().unitPerPackage()))
                        .toList(),
                tray.diets(),
                tray.allergens(),
                valuation
        );
    }
}
