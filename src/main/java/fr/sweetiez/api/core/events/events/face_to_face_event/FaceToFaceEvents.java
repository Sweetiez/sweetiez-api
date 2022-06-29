package fr.sweetiez.api.core.events.events.face_to_face_event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FaceToFaceEvents {

    void save(FaceToFaceEvent event);
    Optional<FaceToFaceEvent> findById(UUID id);
    List<FaceToFaceEvent> findAllPublished();
    List<FaceToFaceEvent> findAll();
}
