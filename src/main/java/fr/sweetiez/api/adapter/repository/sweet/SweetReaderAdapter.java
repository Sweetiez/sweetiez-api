package fr.sweetiez.api.adapter.repository.sweet;

import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.models.sweet.SweetId;
import fr.sweetiez.api.core.sweets.models.sweet.states.State;
import fr.sweetiez.api.core.sweets.models.sweet.Sweets;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SweetReaderAdapter implements SweetsReader {

    private final SweetRepository repository;
    private final SweetMapper sweetMapper;

    public SweetReaderAdapter(SweetRepository repository, SweetMapper sweetMapper) {
        this.repository = repository;
        this.sweetMapper = sweetMapper;
    }

    public Optional<Sweet> findById(SweetId id) {
        return repository.findById(UUID.fromString(id.value())).map(sweetMapper::toDto);
    }

    public Sweets findAllPublished() {
        var sweets = repository.findAllByState(State.PUBLISHED)
                .stream()
                .map(sweetMapper::toDto)
                .collect(Collectors.toSet());

        return new Sweets(sweets);
    }

    public Sweets findAll() {
        var sweets =  repository.findAll()
                .stream()
                .map(sweetMapper::toDto)
                .collect(Collectors.toSet());

        return new Sweets(sweets);
    }
}
