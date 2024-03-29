package fr.sweetiez.api.adapter.repository.events.face_to_face;

import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;
import fr.sweetiez.api.core.events.space.SpaceDTO;
import fr.sweetiez.api.core.events.space.SpaceID;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.infrastructure.repository.events.space.ReservedSpaceEntity;
import fr.sweetiez.api.infrastructure.repository.events.space.ReservedSpaceRepository;
import fr.sweetiez.api.infrastructure.repository.events.space.SpaceEntity;
import fr.sweetiez.api.infrastructure.repository.events.space.SpaceRepository;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpacesAdapter implements Spaces {
    private final SpaceRepository spaceRepository;
    private final ReservedSpaceRepository reservedSpaceRepository;

    public SpacesAdapter(SpaceRepository spaceRepository, ReservedSpaceRepository reservedSpaceRepository) {
        this.spaceRepository = spaceRepository;
        this.reservedSpaceRepository = reservedSpaceRepository;
    }

    public Optional<Space> findById(UUID id) {
        var customer = spaceRepository.findById(id);
        var reservations = reservedSpaceRepository.findAllById(List.of(id))
                .stream()
                .map(reservedSpace -> new Schedule(
                        reservedSpace.start(),
                        reservedSpace.duration())
                )
                .collect(Collectors.toList());

        return customer.isPresent()
                ? Optional.of(new Space(new SpaceID(id), reservations))
                : Optional.empty();
    }

    public void book(Space space, Schedule schedule) {
        reservedSpaceRepository.save(new ReservedSpaceEntity(
                space.getId().getSpaceID(),
                schedule.getStart(),
                Duration.between(schedule.getStart(), schedule.getEnd())));
    }

    public Optional<SpaceDTO> getInfo(UUID id) {
        return spaceRepository.findById(id).map(entity -> new SpaceDTO(
                entity.id(),
                entity.address(),
                entity.zipCode(),
                entity.city(),
                entity.places()));
    }

    public SpaceDTO save(SpaceDTO space) {
        var entity = spaceRepository.save(new SpaceEntity(
                space.id(),
                space.address(),
                space.city(),
                space.zipCode(),
                space.places()));
        return new SpaceDTO(entity.id(), entity.address(), entity.city(), entity.zipCode(), entity.places());
    }

    @Override
    public Collection<SpaceDTO> findAll() {
        return spaceRepository.findAll().stream()
                .map(entity -> new SpaceDTO(
                        entity.id(),
                        entity.address(),
                        entity.city(),
                        entity.zipCode(),
                        entity.places()))
                .collect(Collectors.toList());
    }


}
