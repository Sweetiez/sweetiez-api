package fr.sweetiez.api.core.sweets.services;

import fr.sweetiez.api.core.evaluations.models.EvaluationResponse;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.UnPublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.UpdateSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.*;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SweetService {

    private final SweetsWriter writer;
    private final SweetsReader reader;
    private final EvaluationService evaluationService;

    public SweetService(SweetsWriter writer, SweetsReader reader, EvaluationService evaluationService) {
        this.writer = writer;
        this.reader = reader;
        this.evaluationService = evaluationService;
    }

    public Sweet createSweet(CreateSweetRequest sweet) {
        var sweets = reader.findAll();
        var sweetId = SweetId.generate(sweets);
        var sweetToCreate = new Sweet(sweetId, sweet);

        if (!sweetToCreate.isValid()) {
            throw new InvalidFieldsException();
        }

        var nameAlreadyTaken = sweets.content()
                .stream()
                .map(Sweet::name)
                .collect(Collectors.toSet())
                .contains(sweetToCreate.name());

        if (nameAlreadyTaken) {
            throw new SweetAlreadyExistsException();
        }

        return writer.save(sweetToCreate);
    }

    public Sweet publishSweet(PublishSweetRequest request) {
        var sweetToPublish = reader.findById(new SweetId(request.id()))
                .orElseThrow()
                .publish(request.highlight());

        return writer.save(sweetToPublish);
    }

    public Sweet unPublishSweet(UnPublishSweetRequest request) {
        var sweetToPublish = reader.findById(new SweetId(request.id()))
                .orElseThrow()
                .unPublish();

        return writer.save(sweetToPublish);
    }

    public Sweets retrieveAllSweets() {
        return reader.findAll();
    }

    public Sweets retrievePublishedSweets() {
        var sweets = reader.findAllPublished();

        var banner = sweets.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.BANNER))
                .toList();

        var promoted = sweets.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.PROMOTED))
                .toList();

        var common = sweets.content().stream()
                .filter(sweet -> sweet.states().highlight().equals(Highlight.COMMON))
                .toList();

        var content = new LinkedList<>(banner);
        content.addAll(promoted);
        content.addAll(common);

        return new Sweets(content);
    }

    public DetailedSweetResponse retrieveSweetDetails(String id) {
        var sweet = reader.findById(new SweetId(id)).orElseThrow();
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
                        eval.comment(),
                        eval.voter().toString(),
                        eval.subject(),
                        eval.mark()))
                .toList();

        return new DetailedSweetResponse(sweet, evaluation, comments);
    }

    public AdminDetailedSweetResponse adminRetrieveSweetDetails(String id) {
        var sweet = reader.findById(new SweetId(id)).orElseThrow();

        return new AdminDetailedSweetResponse(sweet);
    }

    public SimpleSweetResponse addImageToSweet(String id, String imageUrl) {
        var sweet = reader.findById(new SweetId(id)).orElseThrow();

        return new SimpleSweetResponse(writer.save(sweet.addImage(imageUrl)));
    }

    public AdminDetailedSweetResponse adminUpdateSweetDetails(UpdateSweetRequest request) {
        var sweet = reader.findById(new SweetId(request.id())).orElseThrow();
        var updatedSweet = new Sweet(sweet.id(), request);
        var updated = writer.save(updatedSweet);

        return new AdminDetailedSweetResponse(updated);
    }
}
