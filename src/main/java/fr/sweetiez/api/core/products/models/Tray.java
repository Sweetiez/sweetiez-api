package fr.sweetiez.api.core.products.models;

import fr.sweetiez.api.core.products.models.common.Description;
import fr.sweetiez.api.core.products.models.common.Name;
import fr.sweetiez.api.core.products.models.common.Price;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.core.products.models.requests.CreateProductRequest;
import fr.sweetiez.api.core.products.models.requests.UpdateSweetRequest;

import java.util.Collection;
import java.util.List;

public class Tray extends Product {
    private final Collection<Sweet> sweets;

    public Tray(ProductID id, Name name, Description description, Price price,
                Details details, Collection<Sweet> sweets) {
        super(id, name, description, price, details);
        this.sweets = sweets;
    }

    public Tray(CreateProductRequest request, Collection<Sweet> sweets) {
        super(null,
                new Name(request.name()),
                new Description(request.description()),
                new Price(request.price()),
                new Details(
                        List.of(),
                        new Characteristics(Highlight.COMMON, State.CREATED, request.flavor()),
                        new Valuation(List.of())));

        this.sweets = sweets;
    }

    public Tray(Tray tray, Details details) {
        super(tray.id(),
                tray.name(),
                tray.description(),
                tray.price(),
                details);

        sweets = tray.sweets();
    }

    public Tray(Tray tray, UpdateSweetRequest request, Collection<Sweet> sweets) {
        super(tray.id,
                new Name(request.name()),
                new Description(request.description()),
                new Price(request.price()),
                new Details(
                        request.images(),
                        new Characteristics(request.highlight(), request.state(), request.flavor()),
                        tray.details().valuation()));

        this.sweets = sweets;
    }

    public boolean isValid() {
        return super.isValid() && sweets != null && !sweets.isEmpty();
    }

    public Tray publish(Highlight highlight) {
        if (!isValid()) {
            return this;
        }

        var characteristics = new Characteristics(highlight, State.PUBLISHED, details.characteristics().flavor());
        var details = new Details(details().images(), characteristics, details().valuation());

        return new Tray(this, details);
    }

    public Tray unpublish() {
        if (!isValid()) {
            return this;
        }

        var characteristics = new Characteristics(
                details.characteristics().highlight(),
                State.NON_PUBLISHED,
                details.characteristics().flavor());

        var details = new Details(details().images(), characteristics, details().valuation());

        return new Tray(this, details);
    }

    public Collection<Sweet> sweets() {
        return sweets;
    }

    public Collection<String> diets() {
        var sweetsDiets = sweetsDietLabels();
        return computeDiets(sweetsDiets);
    }

    private Collection<List<String>> sweetsDietLabels() {
        return sweets.stream()
                .map(Sweet::diets)
                .map(diets -> (List<String>) diets)
                .toList();
    }

    public Collection<String> allergens() {
        return sweets.stream()
                .map(Sweet::allergens)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    public Tray addImage(String imageUrl) {
        var newDetails = details.addImage(imageUrl);
        return new Tray(this, newDetails);
    }

    public Tray deleteImage(String imageUrl) {
        var newDetails = details.deleteImage(imageUrl);
        return new Tray(this, newDetails);
    }

}
