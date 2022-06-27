package fr.sweetiez.api.core.events.use_case.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record RescheduleEventRequest(UUID eventId, LocalDateTime newStart, Duration newDuration) {}
