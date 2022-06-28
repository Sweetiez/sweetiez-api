package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record RescheduleEventRequest(UUID eventId, LocalDateTime newStart, Integer newDuration) {}
