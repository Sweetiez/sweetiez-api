package fr.sweetiez.api.core.events.use_case.models;

import java.time.LocalDateTime;

public record ScheduleResponse(LocalDateTime start, LocalDateTime end) {}
