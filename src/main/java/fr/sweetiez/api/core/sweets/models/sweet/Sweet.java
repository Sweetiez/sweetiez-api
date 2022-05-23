package fr.sweetiez.api.core.sweets.models.sweet;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.UpdateSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.details.*;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;

import java.util.Set;
import java.util.stream.Collectors;

public record Sweet(
        SweetId id,
        Name name,
        Price price,
        States states,
        Details details)
{
    public Sweet(SweetId sweetId, CreateSweetRequest request) {
        this(
                sweetId,
                new Name(request.name()),
                new Price(request.price()),
                new States(Highlight.COMMON, State.CREATED),
                new Details(
                        new Description(request.description()),
                        request.flavor(),
                        Set.of(),
                        new Ingredients(request.ingredients()),
                        5.
                )
        );
    }

    public Sweet(SweetId sweetId, UpdateSweetRequest request) {
        this(
                sweetId,
                new Name(request.name()),
                new Price(request.price()),
                new States(Highlight.valueOf(request.highlight()), State.valueOf(request.state())),
                new Details(
                        new Description(request.description()),
                        request.flavor(),
                        request.images(),
                        new Ingredients(request.ingredients()),
                        request.rating()
                )
        );
    }

    public Sweet publish(Highlight highlight) {
        if (isValid()) {
            var states = new States(highlight, State.PUBLISHED);
            return new Sweet(id, name, price, states, details);
        }

        return this;
    }

    public Sweet unPublish() {
        if (isValid()) {
            var states = new States(this.states.highlight(), State.NON_PUBLISHED);
            return new Sweet(id, name, price, states, details);
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

    public Sweet addImage(String imageUrl) {
        var newDetails = details.addImage(imageUrl);
        return new Sweet(id, name, price, states, newDetails);
    }

    public Sweet deleteImage(String imageUrl) {
        var newDetails = details.deleteImage(imageUrl);
        return new Sweet(id, name, price, states, newDetails);
    }
}
