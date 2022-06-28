package fr.sweetiez.api.core.events.use_case.streaming.models.requests;

import java.util.UUID;

public record SubscribeEventRequest(UUID eventId, UUID subscriber) {}
