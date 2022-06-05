package fr.sweetiez.api.infrastructure.repository.trays;

import fr.sweetiez.api.core.trays.models.tray.states.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrayRepository extends JpaRepository<TrayEntity, UUID> {
    List<TrayEntity> findAllByState(State state);
}
