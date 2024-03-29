package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import fr.sweetiez.api.core.events.events.StatusEvent;

import java.util.UUID;

public record EventAdminResponse(
        UUID id,
        String title,
        String description,
        Location localisation,
        ScheduleResponse schedule,
        Availability availability,
        AnimatorResponse animator,
        StatusEvent status
) {}
