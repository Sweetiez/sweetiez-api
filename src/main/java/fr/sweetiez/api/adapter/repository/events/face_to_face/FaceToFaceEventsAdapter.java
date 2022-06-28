package fr.sweetiez.api.adapter.repository.events.face_to_face;

import fr.sweetiez.api.adapter.shared.FaceToFaceEventMapper;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.infrastructure.repository.events.event.face_to_face.FaceToFaceEventEntity;
import fr.sweetiez.api.infrastructure.repository.events.event.face_to_face.FaceToFaceEventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FaceToFaceEventsAdapter implements FaceToFaceEvents {
    private final FaceToFaceEventRepository repository;
    private final Animators animatorRepository;
    private final Spaces spaceRepository;
    private final FaceToFaceEventMapper mapper;

    public FaceToFaceEventsAdapter(FaceToFaceEventRepository repository, Animators animatorRepository,
                                   Spaces spaceRepository, FaceToFaceEventMapper mapper)
    {
        this.repository = repository;
        this.animatorRepository = animatorRepository;
        this.spaceRepository = spaceRepository;
        this.mapper = mapper;
    }

    public void save(FaceToFaceEvent event) {
        repository.save(mapper.toEntity(event));
    }

    public Optional<FaceToFaceEvent> findById(UUID id) {
        var optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            var entity = optionalEntity.get();
            var animator = animatorRepository.findById(entity.getAnimator()).orElseThrow();
            var space = spaceRepository.findById(entity.getSpace()).orElseThrow();

            return Optional.of(mapper.toDto(entity, animator, space));
        }

        return Optional.empty();
    }

    public List<FaceToFaceEvent> findAllPublished() {
        var entities = repository.findAll()
                .stream()
                .filter(entity -> entity.getStatus() == StatusEvent.PUBLISHED)
                .toList();

        return mapEvents(entities);
    }

    public List<FaceToFaceEvent> findAll() {
        var entities = repository.findAll();
        return mapEvents(entities);
    }

    private ArrayList<FaceToFaceEvent> mapEvents(List<FaceToFaceEventEntity> entities) {
        var events = new ArrayList<FaceToFaceEvent>();

        entities.forEach(entity -> {
            var animator = animatorRepository.findById(entity.getAnimator()).orElseThrow();
            var space = spaceRepository.findById(entity.getSpace()).orElseThrow();
            events.add(mapper.toDto(entity, animator, space));
        });

        return events;
    }
}
