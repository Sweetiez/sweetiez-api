package fr.sweetiez.api.core.evaluations.models;

import java.util.UUID;

public record EvaluationResponse(UUID id, String content, String author, String authorName, UUID subject, Double rating) {}
