package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import java.util.List;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String title,
        String description,
        Location localisation,
        ScheduleResponse schedule,
        Availability availability,
        List<String> subscribers
) {}