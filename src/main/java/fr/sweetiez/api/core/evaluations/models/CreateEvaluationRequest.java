package fr.sweetiez.api.core.evaluations.models;

import java.util.UUID;

public record CreateEvaluationRequest(Integer mark, UUID subject, String content, UUID author) {}
