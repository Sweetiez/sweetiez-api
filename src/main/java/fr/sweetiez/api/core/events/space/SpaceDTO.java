package fr.sweetiez.api.core.events.space;

import fr.sweetiez.api.core.events.use_case.face_to_face.models.CreateSpaceRequest;

import java.util.UUID;

public record SpaceDTO(
       UUID id,
       String address,
       String city,
       String zipCode,
       Integer places
) {

    public SpaceDTO(CreateSpaceRequest request) {
        this(UUID.randomUUID(), request.address(), request.city(), request.zipCode(), request.places());
    }

}
