package fr.sweetiez.api.core.sweets.services;

import fr.sweetiez.api.core.comments.services.CommentService;
import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.responses.DetailedSweetResponse;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;

import java.util.stream.Collectors;

public class SweetService {

    private final SweetsWriter writer;
    private final SweetsReader reader;
    private final CommentService commentService;

    public SweetService(SweetsWriter writer, SweetsReader reader, CommentService commentService) {
        this.writer = writer;
        this.reader = reader;
        this.commentService = commentService;
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

    public Sweets retrievePublishedSweets() {
        return reader.findAllPublished();
    }

    public DetailedSweetResponse retrieveSweetDetails(String id) {
        var sweet = reader.findById(new SweetId(id)).orElseThrow();
        var comments = commentService.retrieveCommentsBySubject(id);

        return new DetailedSweetResponse(sweet, comments);
    }
}
