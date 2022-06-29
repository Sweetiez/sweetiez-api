package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import java.time.LocalDateTime;

public record ScheduleResponse(LocalDateTime start, LocalDateTime end) {}
