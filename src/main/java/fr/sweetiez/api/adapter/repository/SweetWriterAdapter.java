package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.employees.models.EmployeeId;
import fr.sweetiez.api.core.sweets.models.sweet.Sweet;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.infrastructure.repository.SweetRepository;

public class SweetWriterAdapter implements SweetsWriter {

    private final SweetRepository repository;
    private final SweetMapper sweetMapper;

    public SweetWriterAdapter(SweetRepository repository, SweetMapper sweetMapper) {
        this.repository = repository;
        this.sweetMapper = sweetMapper;
    }

    public Sweet save(Sweet sweet, EmployeeId employeeId) {
        var entity = repository.save(sweetMapper.toEntity(sweet, employeeId));
        return sweetMapper.toDto(entity);
    }
}
