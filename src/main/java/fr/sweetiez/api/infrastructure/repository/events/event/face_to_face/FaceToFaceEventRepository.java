package fr.sweetiez.api.infrastructure.repository.events.event.face_to_face;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FaceToFaceEventRepository extends JpaRepository<FaceToFaceEventEntity, UUID> {
}
