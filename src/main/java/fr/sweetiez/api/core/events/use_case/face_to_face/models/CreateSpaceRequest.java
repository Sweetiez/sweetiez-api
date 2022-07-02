package fr.sweetiez.api.core.events.use_case.face_to_face.models;

public record CreateSpaceRequest(
        String address,
        String city,
        String zipCode,
        Integer places
) {}
