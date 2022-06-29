package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEventDto;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.infrastructure.repository.events.event.face_to_face.FaceToFaceEventEntity;

import java.time.Duration;
import java.util.stream.Collectors;

public class FaceToFaceEventMapper {

    private final CustomerMapper customerMapper;

    public FaceToFaceEventMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public FaceToFaceEventEntity toEntity(FaceToFaceEvent event) {
        return new FaceToFaceEventEntity(
                event.getId().eventId(),
                event.getTitle(),
                event.getDescription(),
                event.getAnimator().getId().getAnimatorId(),
                event.getSchedule().getStart(),
                event.getSchedule().getEnd(),
                event.getStatus(),
                event.getSpace().getId().getSpaceID(),
                event.getSubscribers().stream().map(customerMapper::toEntity).collect(Collectors.toList()));
    }

    public FaceToFaceEvent toDto(FaceToFaceEventEntity entity, Animator animator, Space space) {
        var dto = new FaceToFaceEventDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                animator,
                new Schedule(entity.getStart(), Duration.between(entity.getStart(), entity.getEnd())),
                entity.getStatus(),
                space,
                entity.getSubscribers().stream().map(customerMapper::toDto).collect(Collectors.toList()));

        return new FaceToFaceEvent(dto);
    }
}
