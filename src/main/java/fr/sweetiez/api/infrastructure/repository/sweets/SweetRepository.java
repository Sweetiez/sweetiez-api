package fr.sweetiez.api.infrastructure.repository.sweets;

import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SweetRepository extends JpaRepository<SweetEntity, UUID> {
    List<SweetEntity> findAllByState(State state);
}