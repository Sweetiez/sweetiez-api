package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.EventDto;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.infrastructure.repository.events.event.FaceToFaceEventEntity;

import java.time.Duration;

public class FaceToFaceEventMapper {

    private final CustomerMapper customerMapper;

    public FaceToFaceEventMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public FaceToFaceEventEntity toEntity(Event event) {
        return new FaceToFaceEventEntity(
                event.getId().getEventId(),
                event.getTitle(),
                event.getAnimator().getId().getAnimatorId(),
                event.getSchedule().getStart(),
                event.getSchedule().getEnd(),
                event.getStatus(),
                event.getSpace().getId().getSpaceID(),
                event.getSubscribers().stream().map(customerMapper::toEntity).toList());
    }

    public Event toDto(FaceToFaceEventEntity entity, Animator animator, Space space) {
        var dto = new EventDto(
                entity.getId(),
                entity.getTitle(),
                animator,
                new Schedule(entity.getStart(), Duration.between(entity.getStart(), entity.getEnd())),
                entity.getStatus(),
                space,
                entity.getSubscribers().stream().map(customerMapper::toDto).toList());

        return new Event(dto);
    }
}
