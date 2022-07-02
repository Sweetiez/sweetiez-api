package fr.sweetiez.api.core.events.space;

import fr.sweetiez.api.core.events.schedule.Schedule;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Spaces {

    Optional<Space> findById(UUID id);
    void book(Space space, Schedule schedule);
    Optional<SpaceDTO> getInfo(UUID id);

    SpaceDTO save(SpaceDTO space);

    Collection<SpaceDTO> findAll();

}
