package fr.sweetiez.api.infrastructure.repository.events.animator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimatorRepository extends JpaRepository<ReservedAnimatorEntity, UUID> {}
