package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.infrastructure.repository.events.event.FaceToFaceEventEntity;

public class FaceToFaceEventMapper {

    private final CustomerMapper customerMapper;

    public FaceToFaceEventMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public FaceToFaceEventEntity toEntity(Event event) {
        return new FaceToFaceEventEntity(
                event.getId().getEventId(),
                event.getAnimator().getId().getAnimatorId(),
                event.getSchedule().getStart(),
                event.getSchedule().getEnd(),
                event.getStatus(),
                event.getSpace().getId().getSpaceID(),
                event.getSubscribers().stream().map(customerMapper::toEntity).toList());
    }
}
