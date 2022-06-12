package fr.sweetiez.api.adapter.repository.sweet;

import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.ports.ProductsWriter;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetRepository;

public class SweetWriterAdapter implements ProductsWriter<Sweet> {

    private final SweetRepository repository;
    private final SweetMapper sweetMapper;

    public SweetWriterAdapter(SweetRepository repository, SweetMapper sweetMapper) {
        this.repository = repository;
        this.sweetMapper = sweetMapper;
    }

    public Sweet save(Sweet sweet) {
        var entity = repository.save(sweetMapper.toEntity(sweet));
        return sweetMapper.toDto(entity);
    }
}
