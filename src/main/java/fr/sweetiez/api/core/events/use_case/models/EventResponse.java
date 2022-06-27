package fr.sweetiez.api.core.events.use_case.models;

import java.util.UUID;

public record EventResponse(
        UUID id,
        String title,
        String description,
        Location localisation,
        ScheduleResponse schedule,
        Availability availability
) {}