package fr.sweetiez.api.core.events.space;

import java.util.UUID;

public record SpaceDTO(
       UUID id,
       String address,
       String city,
       String zipCode,
       Integer places
) {}
