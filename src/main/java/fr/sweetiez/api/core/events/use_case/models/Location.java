package fr.sweetiez.api.core.events.use_case.models;

import java.util.UUID;

public record Location(UUID id, String address, String zipCode, String city) {}
