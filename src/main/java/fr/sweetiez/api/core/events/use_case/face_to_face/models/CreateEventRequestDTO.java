package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEventRequestDTO(
        UUID animatorId,
        String title,
        String description,
        LocalDateTime startDateTime,
        int duration,
        UUID spaceId
) {}
