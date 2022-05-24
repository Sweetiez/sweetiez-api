package fr.sweetiez.api.core.evaluations.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record Report(
        Long id,
        UUID reporterId,
        UUID evaluationId,
        String reason,
        LocalDateTime creationDate
) {}
