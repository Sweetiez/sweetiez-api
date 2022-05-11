package fr.sweetiez.api.core.sweets.models.sweet;

import fr.sweetiez.api.core.ingredients.models.Ingredients;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.details.Details;
import fr.sweetiez.api.core.sweets.models.sweet.details.Name;
import fr.sweetiez.api.core.sweets.models.sweet.details.Price;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.core.sweets.models.sweet.states.States;

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
                        request.description(),
                        request.flavor(),
                        "",
                        new Ingredients(request.ingredients())
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

    public boolean isValid() {
        return id.isValid()
                && name.isValid()
                && price.isValid()
                && states.isValid()
                && details.isValid();
    }
}