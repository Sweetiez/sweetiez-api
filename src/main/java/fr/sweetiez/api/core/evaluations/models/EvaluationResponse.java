package fr.sweetiez.api.core.evaluations.models;

import java.util.UUID;

public record EvaluationResponse(String content, String author, UUID subject, Double rating) {}
