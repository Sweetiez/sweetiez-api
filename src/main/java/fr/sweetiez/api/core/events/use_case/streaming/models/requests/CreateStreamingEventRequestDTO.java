package fr.sweetiez.api.core.events.use_case.streaming.models.requests;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateStreamingEventRequestDTO(
        UUID animatorId,
        String title,
        String description,
        LocalDateTime startDateTime,
        int duration,
        int places
) {}
