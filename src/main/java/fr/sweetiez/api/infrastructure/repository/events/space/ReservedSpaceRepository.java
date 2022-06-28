package fr.sweetiez.api.infrastructure.repository.events.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservedSpaceRepository extends JpaRepository<ReservedSpaceEntity, UUID> {
}
