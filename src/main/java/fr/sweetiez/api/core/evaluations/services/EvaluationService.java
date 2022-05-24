package fr.sweetiez.api.core.evaluations.services;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;

import java.util.Collection;
import java.util.UUID;

public class EvaluationService {

    private final EvaluationReader reader;
    private final EvaluationWriter writer;
    private final CustomerService customerService;

    public EvaluationService(EvaluationReader reader, EvaluationWriter writer, CustomerService customerService) {
        this.reader = reader;
        this.writer = writer;
        this.customerService = customerService;
    }

    public Collection<Evaluation> retrieveAllBySubject(String id) {
        return reader.retrieveAllBySubject(UUID.fromString(id));
    }

    public Evaluation create(CreateEvaluationRequest request) {
        if (!customerService.exists(new CustomerId(request.author().toString()))) {
            throw new CustomerDoesNotExistException();
        }

        var evaluation = new Evaluation(
                null,
                request.content(),
                request.author(),
                request.subject(),
                request.mark().doubleValue());

        return writer.save(evaluation);
    }

    public Double computeTotalScore(Collection<Evaluation> evaluations) {
        if (evaluations.isEmpty()) return null;

        var sum = evaluations
                .stream()
                .map(Evaluation::mark)
                .reduce(0., Double::sum);

        return sum / evaluations.size();
    }

    public boolean exists(EvaluationId id) {
        return reader.exists(id);
    }

    public void delete(EvaluationId id) {
        writer.delete(id);
    }
}
