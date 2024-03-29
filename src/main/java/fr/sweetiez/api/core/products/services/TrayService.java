package fr.sweetiez.api.core.products.services;

import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.products.models.Product;
import fr.sweetiez.api.core.products.models.SweetWithQuantity;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.Details;
import fr.sweetiez.api.core.products.models.common.details.Valuation;
import fr.sweetiez.api.core.products.models.common.details.characteristics.Highlight;
import fr.sweetiez.api.core.products.models.requests.*;
import fr.sweetiez.api.core.products.models.responses.AdminDetailedTrayResponse;
import fr.sweetiez.api.core.products.models.responses.DetailedTrayResponse;
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

public class TrayService implements ProductService{

    private final ProductsWriter<Tray> writer;
    private final ProductsReader<Tray> reader;
    private final EvaluationService evaluationService;
    private final SweetService sweetService;

    public TrayService(ProductsWriter<Tray> writer, ProductsReader<Tray> reader,
                       EvaluationService evaluationService, SweetService sweetService)
    {
        this.writer = writer;
        this.reader = reader;
        this.evaluationService = evaluationService;
        this.sweetService = sweetService;
    }

    public Tray create(CreateTrayRequest request) {
        var sweetIds = request.sweets().stream().map(SweetWithQuantityRequest::sweetId).toList();
        var sweets = sweetService.retrieveAllById(sweetIds);

        var sweetsWithQuantity = new ArrayList<SweetWithQuantity>();
        for (int i = 0; i < sweets.size(); i++) {
            var sweet = sweets.stream().toList().get(i);
            var quantity = request.sweets().get(i).quantity();
            sweetsWithQuantity.add(new SweetWithQuantity(null, sweet, quantity));
        }

        var trayToCreate = new Tray(request, sweetsWithQuantity);

        if (!trayToCreate.isValid()) {
            throw new InvalidFieldsException();
        }

        var trays = reader.findAll();
        var nameAlreadyTaken = trays
                .stream()
                .map(Tray::name)
                .collect(Collectors.toSet())
                .contains(trayToCreate.name());

        if (nameAlreadyTaken) {
            throw new ProductAlreadyExistsException();
        }

        return writer.save(trayToCreate);
    }

    public Tray publish(PublishProductRequest request) {
        var trayToPublish = reader.findById(new ProductID(request.id()))
                .orElseThrow()
                .publish(request.highlight());

        return writer.save(trayToPublish);
    }

    public Tray unpublish(UnpublishProductRequest request) {
        var trayToUnpublish = reader.findById(new ProductID(request.id()))
                .orElseThrow()
                .unpublish();

        return writer.save(trayToUnpublish);
    }

    public Collection<Tray> retrieveAll() {
        return reader.findAll();
    }

    public Collection<Tray> retrieveAllPublished() {
        var trays = reader.findAllPublished()
                .stream()
                .map(tray -> {
                    var evaluations = evaluationService.retrieveAllBySubject(tray.id().value());
                    var valuation = new Valuation(evaluations);
                    return new Tray(tray, new Details(
                            tray.details().images(),
                            tray.details().characteristics(),
                            valuation));})
                .toList();

        var banner = trays.stream()
                .filter(tray -> tray.details().characteristics().highlight().equals(Highlight.BANNER))
                .toList();

        var promoted = trays.stream()
                .filter(Tray -> Tray.details().characteristics().highlight().equals(Highlight.PROMOTED))
                .toList();

        var common = trays.stream()
                .filter(Tray -> Tray.details().characteristics().highlight().equals(Highlight.COMMON))
                .toList();

        var publishedTrays = new LinkedList<>(banner);
        publishedTrays.addAll(promoted);
        publishedTrays.addAll(common);

        return publishedTrays;
    }

    public DetailedTrayResponse retrieveDetailsOf(ProductID id) {
        var tray = reader.findById(id).orElseThrow();
        var evaluations = evaluationService.retrieveAllBySubject(id.value());
        var valuation = new Valuation(evaluations);

        return new DetailedTrayResponse(tray, new ValuationResponse(valuation));
    }

    public AdminDetailedTrayResponse adminRetrieveDetailsOf(ProductID id) {
        var tray = reader.findById(id).orElseThrow();
        return new AdminDetailedTrayResponse(tray);
    }

    public SimpleProductResponse addImageTo(ProductID id, String imageUrl) {
        var tray = reader.findById(id).orElseThrow();
        return new SimpleProductResponse(writer.save(tray.addImage(imageUrl)));
    }

    public AdminDetailedTrayResponse adminUpdateTrayDetails(UpdateTrayRequest request) {
        var tray = reader.findById(new ProductID(request.id())).orElseThrow();
        var sweetIds = request.sweets().stream().map(SweetWithQuantityRequest::sweetId).toList();
        var sweets = sweetService.retrieveAllById(sweetIds);

        var sweetsWithQuantity = new ArrayList<SweetWithQuantity>();
        for (int i = 0; i < sweets.size(); i++) {
            var sweet = sweets.stream().toList().get(i);
            var quantity = request.sweets().stream().toList().get(i).quantity();
            sweetsWithQuantity.add(new SweetWithQuantity(null, sweet, quantity));
        }
        var updatedTray = new Tray(tray, request, sweetsWithQuantity);

        return new AdminDetailedTrayResponse(writer.save(updatedTray));
    }

    public SimpleProductResponse adminDeleteImageFrom(ProductID id, DeleteImageRequest request) {
        var sweet = reader.findById(id).orElseThrow();
        return new SimpleProductResponse(writer.save(sweet.deleteImage(request.imageUrl())));
    }

    public Collection<Tray> retrieveAllById(Collection<UUID> ids) {
        var existingTrays = reader.findAll();
        var trays = new ArrayList<Tray>();

        ids.forEach(id -> existingTrays
                .forEach(tray -> {
                    if (tray.id().value().equals(id)) trays.add(tray);
                })
        );

        return trays;
    }

    public Collection<Product> retrieveAllProductsByIds(Collection<UUID> productIds) {
        return retrieveAllById(productIds).stream().map(tray -> (Product) tray).toList();
    }
}
