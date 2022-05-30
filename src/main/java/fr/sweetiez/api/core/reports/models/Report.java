package fr.sweetiez.api.core.reports.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record Report(
        UUID id,
        UUID reporterId,
        UUID evaluationId,
        String reason,
        String content,
        LocalDateTime creationDate
) {}
