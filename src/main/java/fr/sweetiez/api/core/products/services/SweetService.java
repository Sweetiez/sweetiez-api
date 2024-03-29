package fr.sweetiez.api.core.products.services;

import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.ingredients.models.Ingredient;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
import fr.sweetiez.api.core.products.models.Product;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.requests.*;
import fr.sweetiez.api.core.products.models.responses.AdminDetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.products.models.responses.SimpleProductResponse;
import fr.sweetiez.api.core.products.models.responses.ValuationResponse;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.core.products.ports.ProductsWriter;
import fr.sweetiez.api.core.products.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.products.services.exceptions.ProductAlreadyExistsException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

public class SweetService implements ProductService{

    private final ProductsWriter<Sweet> writer;
    private final ProductsReader<Sweet> reader;
    private final EvaluationService evaluationService;
    private final IngredientService ingredientService;

    public SweetService(ProductsWriter<Sweet> writer, ProductsReader<Sweet> reader,
                        EvaluationService evaluationService, IngredientService ingredientService)
    {
        this.writer = writer;
        this.reader = reader;
        this.evaluationService = evaluationService;
        this.ingredientService = ingredientService;
    }

    public Sweet create(CreateSweetRequest request) {
        var ingredients = ingredientService.retrieveAllById(request.ingredients());
        var sweetToCreate = new Sweet(request, ingredients);

        if (!sweetToCreate.isValid()) {
            throw new InvalidFieldsException();
        }

        var sweets = reader.findAll();
        var nameAlreadyTaken = sweets
                .stream()
                .map(Sweet::name)
                .collect(Collectors.toSet())
                .contains(sweetToCreate.name());

        if (nameAlreadyTaken) {
            throw new ProductAlreadyExistsException();
        }

        return writer.save(sweetToCreate);
    }

    public Sweet publish(PublishProductRequest request) {
        var sweetToPublish = reader.findById(new ProductID(request.id()))
                .orElseThrow()
                .publish(request.highlight());

        return writer.save(sweetToPublish);
    }

    public Sweet unpublish(UnpublishProductRequest request) {
        var sweetToUnpublish = reader.findById(new ProductID(request.id()))
                .orElseThrow()
                .unpublish();

        return writer.save(sweetToUnpublish);
    }

    public Collection<Sweet> retrieveAll() {
        return reader.findAll();
    }

    public Collection<DetailedSweetResponse> retrieveAllPublished() {
        var sweets = reader.findAllPublished()
                .stream()
                .map(sweet -> {
                    var evaluations = evaluationService.retrieveAllBySubject(sweet.id().value());
                    var valuation = new Valuation(evaluations);
                    return new DetailedSweetResponse(sweet, new ValuationResponse(valuation));})
                .toList();

        var banner = sweets.stream()
                .filter(sweet -> sweet.highlight().equals(Highlight.BANNER))
                .toList();

        var promoted = sweets.stream()
                .filter(sweet -> sweet.highlight().equals(Highlight.PROMOTED))
                .toList();

        var common = sweets.stream()
                .filter(sweet -> sweet.highlight().equals(Highlight.COMMON))
                .toList();

        var publishedSweets = new LinkedList<>(banner);
        publishedSweets.addAll(promoted);
        publishedSweets.addAll(common);

        return publishedSweets;
    }

    public void deleteIngredientContainedInSweets(UUID ingredientId) {
        var ingredient = ingredientService.findById(ingredientId).orElseThrow();
        retrieveAll()
                .stream()
                .filter(sweet -> sweet.ingredients().contains(ingredient))
                .forEach(sweet -> {
                    System.out.println(sweet);
                    sweet.ingredients().remove(ingredient);
                    writer.save(sweet);
                });

        ingredientService.deleteIngredient(ingredient.id());
    }

    public DetailedSweetResponse retrieveDetailsOf(ProductID id) {
        var sweet = reader.findById(id).orElseThrow();
        var evaluations = evaluationService.retrieveAllBySubject(id.value());
        var valuation = new Valuation(evaluations);
        return new DetailedSweetResponse(sweet, new ValuationResponse(valuation));
    }

    public AdminDetailedSweetResponse adminRetrieveDetailsOf(ProductID id) {
        var sweet = reader.findById(id).orElseThrow();
        return new AdminDetailedSweetResponse(sweet);
    }

    public SimpleProductResponse addImageTo(ProductID id, String imageUrl) {
        var sweet = reader.findById(id).orElseThrow();
        return new SimpleProductResponse(writer.save(sweet.addImage(imageUrl)));
    }

    public AdminDetailedSweetResponse adminUpdateDetails(UpdateSweetRequest request) {
        var sweet = reader.findById(new ProductID(request.id())).orElseThrow();
        var ingredients = ingredientService.retrieveAllById(request.ingredients());
        var updatedSweet = new Sweet(sweet, request, ingredients);

        return new AdminDetailedSweetResponse(writer.save(updatedSweet));
    }

    public SimpleProductResponse adminDeleteImageFrom(ProductID id, DeleteImageRequest request) {
        var sweet = reader.findById(id).orElseThrow();
        return new SimpleProductResponse(writer.save(sweet.deleteImage(request.imageUrl())));
    }

    public Collection<Sweet> retrieveAllById(Collection<UUID> ids) {
        var existingSweets = reader.findAll();
        var sweets = new ArrayList<Sweet>();

        ids.forEach(id -> existingSweets
                .forEach(sweet -> {
                    if (sweet.id().value().equals(id)) sweets.add(sweet);
                })
        );

        return sweets;
    }

    public Collection<Product> retrieveAllProductsByIds(Collection<UUID> productIds) {
        return retrieveAllById(productIds).stream().map(sweet -> (Product) sweet).toList();
    }
}
