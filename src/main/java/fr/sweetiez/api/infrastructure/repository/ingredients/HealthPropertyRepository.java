package fr.sweetiez.api.infrastructure.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthPropertyRepository extends JpaRepository<HealthPropertyEntity, Long> {}
