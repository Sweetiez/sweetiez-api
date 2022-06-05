package fr.sweetiez.api.core.trays.services;

import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.trays.models.requests.*;
import fr.sweetiez.api.core.trays.models.responses.AdminDetailedTrayResponse;
import fr.sweetiez.api.core.trays.models.responses.DetailedTrayResponse;
import fr.sweetiez.api.core.trays.models.responses.Evaluation;
import fr.sweetiez.api.core.trays.models.responses.SimpleTrayResponse;
import fr.sweetiez.api.core.trays.models.tray.Tray;
import fr.sweetiez.api.core.trays.models.tray.TrayId;
import fr.sweetiez.api.core.trays.models.tray.Trays;
import fr.sweetiez.api.core.trays.models.tray.states.Highlight;
import fr.sweetiez.api.core.trays.ports.TraysReader;
import fr.sweetiez.api.core.trays.ports.TraysWriter;
import fr.sweetiez.api.core.trays.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.trays.services.exceptions.TrayAlreadyExistsException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrayService {

    private final TraysWriter writer;
    private final TraysReader reader;
    private final EvaluationService evaluationService;
    private final CustomerService customerService;

    public TrayService(TraysWriter writer, TraysReader reader, EvaluationService evaluationService, CustomerService customerService) {
        this.writer = writer;
        this.reader = reader;
        this.evaluationService = evaluationService;
        this.customerService = customerService;
    }

    public Tray createSweet(CreateTrayRequest sweet) {
        var sweets = reader.findAll();
        var trayToCreate = new Tray(new TrayId(UUID.randomUUID().toString()), sweet);

        if (!trayToCreate.isValid()) {
            throw new InvalidFieldsException();
        }

        var nameAlreadyTaken = sweets.content()
                .stream()
                .map(Tray::name)
                .collect(Collectors.toSet())
                .contains(trayToCreate.name());

        if (nameAlreadyTaken) {
            throw new TrayAlreadyExistsException();
        }

        return writer.save(trayToCreate);
    }

    public Tray publishSweet(PublishTrayRequest request) {
        var sweetToPublish = reader.findById(new TrayId(request.id()))
                .orElseThrow()
                .publish(request.highlight());

        return writer.save(sweetToPublish);
    }

    public Tray unPublishSweet(UnpublishTrayRequest request) {
        var sweetToPublish = reader.findById(new TrayId(request.id().toString()))
                .orElseThrow()
                .unPublish();

        return writer.save(sweetToPublish);
    }

    public Trays retrieveAllTrays() {
        return reader.findAll();
    }

    public Trays retrievePublishedTrays() {
        var trays = reader.findAllPublished();

        var banner = trays.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.BANNER))
                .toList();

        var promoted = trays.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.PROMOTED))
                .toList();

        var common = trays.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.COMMON))
                .toList();

        var content = new LinkedList<>(banner);
        content.addAll(promoted);
        content.addAll(common);

        return new Trays(content);
    }

    public DetailedTrayResponse retrieveTrayDetails(String id) {
        var tray = reader.findById(new TrayId(id)).orElseThrow();
        var evaluations = evaluationService.retrieveAllBySubject(id);
        var mark = evaluationService.computeTotalScore(evaluations);
        var votes = new HashMap<Integer, Integer>();

        for(int i = 5; i >= 1; i--) {
            var stars = i;
            var voters =  evaluations.stream()
                    .filter(evaluation -> evaluation.mark() == stars)
                    .toList()
                    .size();
            var ratio = voters != 0 ? evaluations.size() / voters : 0;

            votes.put(stars, ratio);
        }

        var evaluation = new Evaluation(mark, evaluations.size(), votes);
        var comments = evaluations
                .stream()
                .map(eval -> new EvaluationResponse(
                        UUID.fromString(eval.id().value()),
                        eval.comment(),
                        eval.voter().toString(),
                        customerService.findById(eval.voter().toString()).firstName(),
                        eval.subject(),
                        eval.mark(),
                        eval.date()))
                .toList();

        return new DetailedTrayResponse(tray, evaluation, comments);
    }

    public AdminDetailedTrayResponse adminRetrieveTrayDetails(String id) {
        var tray = reader.findById(new TrayId(id)).orElseThrow();
        return new AdminDetailedTrayResponse(tray);
    }

    public SimpleTrayResponse addImageToTray(String id, String imageUrl) {
        var tray = reader.findById(new TrayId(id)).orElseThrow();
        return new SimpleTrayResponse(writer.save(tray.addImage(imageUrl)));
    }

    public AdminDetailedTrayResponse adminUpdateTrayDetails(UpdateTrayRequest request) {
        var tray = reader.findById(new TrayId(request.id())).orElseThrow();
        var updatedTray = new Tray(tray.id(), request);
        var updated = writer.save(updatedTray);

        return new AdminDetailedTrayResponse(updated);
    }

    public SimpleTrayResponse adminDeleteImageFromTray(String id, DeleteImageRequest request) {
        var tray = reader.findById(new TrayId(id)).orElseThrow();
        return new SimpleTrayResponse(writer.save(tray.deleteImage(request.imageUrl())));
    }
}
