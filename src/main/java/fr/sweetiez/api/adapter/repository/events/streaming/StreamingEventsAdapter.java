package fr.sweetiez.api.adapter.repository.events.streaming;

import fr.sweetiez.api.adapter.shared.StreamingEventMapper;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.infrastructure.repository.events.event.streaming.StreamingEventEntity;
import fr.sweetiez.api.infrastructure.repository.events.event.streaming.StreamingEventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StreamingEventsAdapter implements StreamingEvents {

    private final StreamingEventRepository repository;
    private final Animators animators;
    private final StreamingEventMapper mapper;

    public StreamingEventsAdapter(StreamingEventRepository repository, Animators animators, StreamingEventMapper mapper)
    {
        this.animators = animators;
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(StreamingEvent event) {
        repository.save(mapper.toEntity(event));
    }

    public Optional<StreamingEvent> findById(UUID id) {
        var optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            var entity = optionalEntity.get();
            var animator = animators.findById(entity.getAnimator()).orElseThrow();

            return Optional.of(mapper.toDto(entity, animator));
        }

        return Optional.empty();
    }

    public List<StreamingEvent> findAllPublished() {
        var entities = repository.findAll()
                .stream()
                .filter(entity -> entity.getStatus() == StatusEvent.PUBLISHED)
                .toList();

        return mapEvents(entities);
    }

    public List<StreamingEvent> findAll() {
        var entities = repository.findAll();
        return mapEvents(entities);
    }

    private List<StreamingEvent> mapEvents(List<StreamingEventEntity> entities) {
        return entities.stream()
                .map(entity -> {
                    var animator = animators.findById(entity.getAnimator()).orElseThrow();
                    return mapper.toDto(entity, animator);
                })
                .collect(Collectors.toList());
    }
}
