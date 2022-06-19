package fr.sweetiez.api.core.evaluations.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record EvaluationResponse(
        UUID id,
        String content,
        VoterResponse voter,
        UUID subject,
        Double mark,
        LocalDateTime date) {}
