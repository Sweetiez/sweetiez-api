package fr.sweetiez.api.core.evaluations.services;

import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.customers.services.exceptions.CustomerDoesNotExistException;
import fr.sweetiez.api.core.evaluations.models.CreateEvaluationRequest;
import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.evaluations.models.EvaluationId;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;
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

    public Collection<Evaluation> retrieveAllBySubject(UUID id) {
        return reader.retrieveAllBySubject(id);
    }

    public Evaluation getById(String id) throws EvaluationDoesNotExistException {
        return this.reader.findById(id).orElseThrow();
    }

    public Evaluation create(CreateEvaluationRequest request) {
        try {
            var customer = customerService.findById(request.author().toString());
            var evaluation = new Evaluation(
                    null,
                    request.content(),
                    customer,
                    request.subject(),
                    request.mark().doubleValue(),
                    LocalDateTime.now());

            return writer.save(evaluation);
        }
        catch (NoSuchElementException exception) {
            throw new CustomerDoesNotExistException();
        }
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
