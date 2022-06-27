package fr.sweetiez.api.core.events.use_case.models;

import java.util.UUID;

public record SubscribeEventRequest(UUID eventId, UUID subscriber) {}
