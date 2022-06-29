package fr.sweetiez.api.core.events.use_case.face_to_face.models;

import java.util.UUID;

public record SubscribeEventRequest(UUID eventId, UUID subscriber) {}
