package fr.sweetiez.api.core.events.use_case.streaming.models.responses;

import java.util.UUID;

public record Location(UUID id, String address, String zipCode, String city) {}
