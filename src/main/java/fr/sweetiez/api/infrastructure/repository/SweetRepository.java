package fr.sweetiez.api.infrastructure.repository;

import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SweetRepository extends JpaRepository<SweetEntity, Long> {
    Optional<SweetEntity> findById(String id);
    List<SweetEntity> findAllByState(State state);
}
