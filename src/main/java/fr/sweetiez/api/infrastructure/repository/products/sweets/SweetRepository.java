package fr.sweetiez.api.infrastructure.repository.products.sweets;

import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SweetRepository extends JpaRepository<SweetEntity, UUID> {
    List<SweetEntity> findAllByState(State state);
}
