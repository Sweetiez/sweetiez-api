package fr.sweetiez.api.core.evaluations.models;

import java.util.UUID;

public record Evaluation(EvaluationId id, String comment, UUID voter, UUID subject, Double mark) {}
