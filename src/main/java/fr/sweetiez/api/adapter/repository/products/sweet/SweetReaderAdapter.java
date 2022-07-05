package fr.sweetiez.api.adapter.repository.products.sweet;

import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class SweetReaderAdapter implements ProductsReader<Sweet> {

    private final SweetRepository repository;
    private final SweetMapper sweetMapper;

    public SweetReaderAdapter(SweetRepository repository, SweetMapper sweetMapper) {
        this.repository = repository;
        this.sweetMapper = sweetMapper;
    }

    public Optional<Sweet> findById(ProductID id) {
        return repository.findById(id.value()).map(sweetMapper::toDto);
    }

    public Collection<Sweet> findAllPublished() {
        return repository.findAllByState(State.PUBLISHED)
                .stream()
                .map(sweetMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<Sweet> findAll() {
        return repository.findAll()
                .stream()
                .map(sweetMapper::toDto)
                .collect(Collectors.toList());
    }
}
