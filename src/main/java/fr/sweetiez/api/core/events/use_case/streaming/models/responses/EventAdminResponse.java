package fr.sweetiez.api.core.events.use_case.streaming.models.responses;

import fr.sweetiez.api.core.events.events.StatusEvent;

import java.util.UUID;

public record EventAdminResponse(
        UUID id,
        String title,
        String description,
        ScheduleResponse schedule,
        Availability availability,
        AnimatorResponse animator,
        StatusEvent status
) {}
