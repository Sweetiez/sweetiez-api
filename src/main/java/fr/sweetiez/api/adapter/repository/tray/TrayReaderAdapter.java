package fr.sweetiez.api.adapter.repository.tray;

import fr.sweetiez.api.adapter.shared.TrayMapper;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.models.common.details.characteristics.State;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.infrastructure.repository.products.trays.TrayRepository;

import java.util.Collection;
import java.util.Optional;

public class TrayReaderAdapter implements ProductsReader<Tray> {

    private final TrayRepository repository;
    private final TrayMapper mapper;

    public TrayReaderAdapter(TrayRepository repository, TrayMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<Tray> findById(ProductID id) {
        return repository.findById(id.value()).map(mapper::toDto);
    }

    public Collection<Tray> findAllPublished() {
        return repository.findAllByState(State.PUBLISHED)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Collection<Tray> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
