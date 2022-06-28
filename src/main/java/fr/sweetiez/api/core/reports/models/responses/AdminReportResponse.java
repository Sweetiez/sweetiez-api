package fr.sweetiez.api.core.reports.models.responses;

import fr.sweetiez.api.core.evaluations.models.Evaluation;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminReportResponse(
    UUID id,
    UUID reporterId,
    String comment,
    String reason,
    String content,
    LocalDateTime creationDate
) {}
