package fr.sweetiez.api.core.events.use_case.streaming.models.requests;

import java.time.LocalDateTime;
import java.util.UUID;

public record RescheduleEventRequest(UUID eventId, LocalDateTime newStart, Integer newDuration) {}
