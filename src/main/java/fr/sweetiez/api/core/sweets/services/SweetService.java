package fr.sweetiez.api.core.sweets.services;

import fr.sweetiez.api.core.sweets.models.requests.CreateSweetRequest;
import fr.sweetiez.api.core.sweets.models.requests.PublishSweetRequest;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.employees.models.EmployeeId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.services.exceptions.InvalidFieldsException;
import fr.sweetiez.api.core.sweets.services.exceptions.SweetAlreadyExistsException;

import java.util.stream.Collectors;

public class SweetService {

    private final SweetsWriter writer;
    private final SweetsReader reader;

    public SweetService(SweetsWriter writer, SweetsReader reader) {
        this.writer = writer;
        this.reader = reader;
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

        return writer.save(sweetToCreate, new EmployeeId(sweet.employee()));
    }

    public Sweet publishSweet(PublishSweetRequest request) {
        var sweetToPublish = reader.findById(new SweetId(request.id()))
                .orElseThrow()
                .publish(request.highlight());

        return writer.save(sweetToPublish, new EmployeeId(request.employee()));
    }

    public Sweets retrievePublishedSweets() {
        return reader.findAllPublished();
    }
}
