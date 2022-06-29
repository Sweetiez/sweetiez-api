package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEventDto;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.infrastructure.repository.events.event.streaming.StreamingEventEntity;

import java.time.Duration;
import java.util.stream.Collectors;

public class StreamingEventMapper {

    private final CustomerMapper customerMapper;

    public StreamingEventMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public StreamingEventEntity toEntity(StreamingEvent event) {
        return new StreamingEventEntity(
                event.id().eventId(),
                event.title(),
                event.description(),
                event.animator().getId().getAnimatorId(),
                event.schedule().getStart(),
                event.schedule().getEnd(),
                event.status(),
                event.places(),
                event.subscribers().stream().map(customerMapper::toEntity).collect(Collectors.toList()));
    }

    public StreamingEvent toDto(StreamingEventEntity entity, Animator animator) {
        var dto = new StreamingEventDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                animator,
                new Schedule(entity.getStart(), Duration.between(entity.getStart(), entity.getEnd())),
                entity.getStatus(),
                entity.getPlaces(),
                entity.getSubscribers().stream().map(customerMapper::toDto).collect(Collectors.toList()));

        return new StreamingEvent(dto);
    }
}
