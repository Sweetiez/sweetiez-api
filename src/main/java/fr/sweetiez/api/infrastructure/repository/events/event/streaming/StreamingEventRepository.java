package fr.sweetiez.api.infrastructure.repository.events.event.streaming;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StreamingEventRepository extends JpaRepository<StreamingEventEntity, UUID> {}
