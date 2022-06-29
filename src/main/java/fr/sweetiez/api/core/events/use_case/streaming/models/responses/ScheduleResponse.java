package fr.sweetiez.api.core.events.use_case.streaming.models.responses;

import java.time.LocalDateTime;

public record ScheduleResponse(LocalDateTime start, LocalDateTime end) {}
