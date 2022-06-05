package fr.sweetiez.api.core.trays.models.tray;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.trays.models.requests.CreateTrayRequest;
import fr.sweetiez.api.core.trays.models.requests.UpdateTrayRequest;
import fr.sweetiez.api.core.trays.models.tray.details.Description;
import fr.sweetiez.api.core.trays.models.tray.details.Details;
import fr.sweetiez.api.core.trays.models.tray.details.Name;
import fr.sweetiez.api.core.trays.models.tray.details.Price;
import fr.sweetiez.api.core.trays.models.tray.states.Highlight;
import fr.sweetiez.api.core.trays.models.tray.states.State;
import fr.sweetiez.api.core.trays.models.tray.states.States;

import java.util.Collection;
import java.util.Set;

public record Tray(
        TrayId id,
        Name name,
        Price price,
        States states,
        Details details)
{
    public Tray(TrayId trayId, CreateTrayRequest request) {
        this(
                trayId,
                new Name(request.name()),
                new Price(request.price()),
                new States(Highlight.COMMON, State.CREATED),
                new Details(
                        new Description(request.description()),
                        request.flavor(),
                        Set.of(),
                        request.sweets(),
                        5.
                )
        );
    }

    public Tray(TrayId trayId, UpdateTrayRequest request) {
        this(
                trayId,
                new Name(request.name()),
                new Price(request.price()),
                new States(Highlight.valueOf(request.highlight()), State.valueOf(request.state())),
                new Details(
                        new Description(request.description()),
                        request.flavor(),
                        request.images(),
                        request.sweets(),
                        request.rating()
                )
        );
    }

    public Tray publish(Highlight highlight) {
        if (isValid()) {
            var states = new States(highlight, State.PUBLISHED);
            return new Tray(id, name, price, states, details);
        }

        return this;
    }

    public Tray unPublish() {
        if (isValid()) {
            var states = new States(this.states.highlight(), State.NON_PUBLISHED);
            return new Tray(id, name, price, states, details);
        }
        return this;
    }

    public boolean isValid() {
        return id.isValid()
                && name.isValid()
                && price.isValid()
                && states.isValid()
                && details.isValid();
    }

    public Tray addImage(String imageUrl) {
        var newDetails = details.addImage(imageUrl);
        return new Tray(id, name, price, states, newDetails);
    }

    public Tray deleteImage(String imageUrl) {
        var newDetails = details.deleteImage(imageUrl);
        return new Tray(id, name, price, states, newDetails);
    }
}
