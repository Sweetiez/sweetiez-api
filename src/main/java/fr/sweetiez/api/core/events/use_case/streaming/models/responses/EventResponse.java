package fr.sweetiez.api.core.events.use_case.streaming.models.responses;

import fr.sweetiez.api.core.customers.models.CustomerId;

import java.util.List;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String title,
        String description,
        ScheduleResponse schedule,
        Availability availability,
        List<String> subscribers
) {}