package fr.sweetiez.api.adapter.repository.events;

import fr.sweetiez.api.adapter.shared.FaceToFaceEventMapper;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.event.Events;
import fr.sweetiez.api.infrastructure.repository.events.event.FaceToFaceEventRepository;

public class FaceToFaceEventsAdapter implements Events {
    private final FaceToFaceEventRepository repository;
    private final FaceToFaceEventMapper mapper;

    public FaceToFaceEventsAdapter(FaceToFaceEventRepository repository, FaceToFaceEventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(Event event) {
        repository.save(mapper.toEntity(event));
    }
}
