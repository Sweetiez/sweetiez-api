package fr.sweetiez.api.core.products.models.responses;

import fr.sweetiez.api.core.products.models.Tray;

import java.util.Collection;
import java.util.UUID;

public record AdminDetailedTrayResponse(
        UUID id,
        String name,
        double price,
        String description,
        String flavor,
        Collection<String> images,
        Collection<SimpleSweetWithQuantity> sweets,
        ValuationResponse valuations,
        String state,
        String highlight
)
{
    public AdminDetailedTrayResponse(Tray tray) {
        this(
                tray.id().value(),
                tray.name().value(),
                tray.price().unitPrice().doubleValue(),
                tray.description().content(),
                tray.details().characteristics().flavor().name(),
                tray.details().images(),
                tray.sweets().stream()
                        .map(sweetQty -> new SimpleSweetWithQuantity(
                                new SimpleProductResponse(
                                        sweetQty.sweet()),
                                sweetQty.quantity() * sweetQty.sweet().price().unitPerPackage()))
                        .toList(),
                new ValuationResponse(tray.details().valuation()),
                tray.details().characteristics().state().name(),
                tray.details().characteristics().highlight().name()
        );
    }
}
