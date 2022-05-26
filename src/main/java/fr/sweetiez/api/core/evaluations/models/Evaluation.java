package fr.sweetiez.api.core.evaluations.models;

import java.time.LocalDate;
import java.util.UUID;

public record Evaluation(EvaluationId id, String comment, UUID voter, UUID subject, Double mark, LocalDate date) {}
