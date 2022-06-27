package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.core.events.space.SpaceID;
import fr.sweetiez.api.infrastructure.repository.events.space.ReservedSpaceEntity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceMapper {
    public Space toDto(List<ReservedSpaceEntity> entities) {
        var reservations = entities
                .stream()
                .map(entity -> new Schedule(entity.start(), entity.duration()))
                .collect(Collectors.toList());
        return new Space(new SpaceID(entities.get(0).id()), reservations);
    }

    public List<ReservedSpaceEntity> toReservedEntity(Space space) {
        var entities = new ArrayList<ReservedSpaceEntity>();

        space.getReservations().forEach(reservation -> entities.add(new ReservedSpaceEntity(
                space.getId().getSpaceID(),
                reservation.getStart(),
                Duration.between(reservation.getStart(), reservation.getEnd()))));

        return entities;
    }
}


