package fr.sweetiez.api.core.products.services;

import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.ProductID;
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

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TrayService {

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

    public Tray create(CreateProductRequest request) {
        var sweets = sweetService.retrieveAllById(request.composition());
        var trayToCreate = new Tray(request, sweets);

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
        var trays = reader.findAllPublished();

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

    public AdminDetailedTrayResponse adminUpdateTrayDetails(UpdateProductRequest request) {
        var tray = reader.findById(new ProductID(request.id())).orElseThrow();
        var sweets = sweetService.retrieveAllById(request.composition());
        var updatedSweet = new Tray(tray, request, sweets);

        return new AdminDetailedTrayResponse(writer.save(updatedSweet));
    }

    public SimpleProductResponse adminDeleteImageFrom(ProductID id, DeleteImageRequest request) {
        var sweet = reader.findById(id).orElseThrow();
        return new SimpleProductResponse(writer.save(sweet.deleteImage(request.imageUrl())));
    }
}
