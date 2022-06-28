package fr.sweetiez.api.core.evaluations.models;

import fr.sweetiez.api.core.customers.models.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record Evaluation(
        EvaluationId id,
        String comment,
        Customer voter,
        UUID subject,
        Double mark,
        LocalDateTime date) {}
