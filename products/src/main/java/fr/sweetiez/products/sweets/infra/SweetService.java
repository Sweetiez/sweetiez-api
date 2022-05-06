package fr.sweetiez.products.sweets.infra;

import fr.sweetiez.products.common.validators.FieldValidator;
import fr.sweetiez.products.common.Highlight;
import fr.sweetiez.products.common.State;
import fr.sweetiez.products.sweets.domain.Sweet;
import fr.sweetiez.products.sweets.domain.Sweets;
import fr.sweetiez.products.sweets.domain.exceptions.InvalidFieldsException;
import fr.sweetiez.products.sweets.domain.exceptions.SweetAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SweetService implements Sweets {

    private final SweetRepository repository;
    private final FieldValidator validator;

    public Sweet create(Sweet sweet, UUID creator) {
        if (validator.hasErrors()) {
            throw new InvalidFieldsException();
        }

        SweetEntity entity = new SweetEntity(sweet, creator);
        entity.setId(getValidRandomID());

        if (repository.existsByName(entity.getName())) {
            throw new SweetAlreadyExistsException();
        }

        return repository.save(entity).toSweet();
    }

    @Transactional
    public Sweet publish(String sweetId, Highlight highlight, UUID employee) {
        SweetEntity entity = repository.findById(sweetId).orElseThrow();

        entity.setState(State.PUBLISHED);
        entity.setHighlight(highlight);
        entity.setUpdateAuthor(employee);
        entity.setUpdated(LocalDateTime.now());

        return repository.save(entity).toSweet();
    }

    public Set<Sweet> all() {
        return repository.findAll().stream()
            .map(SweetEntity::toSweet)
            .collect(Collectors.toSet());
    }

    public Sweet findById(UUID id) {
        Optional<SweetEntity> optionalSweetEntity = repository.findById(id.toString());
        return optionalSweetEntity.map(SweetEntity::toSweet).orElseThrow();
    }

    private String getValidRandomID() {
        String id;
        Set<String> ids = repository
                .findAll()
                .stream()
                .map(SweetEntity::getId)
                .collect(Collectors.toSet());

        do { id = UUID.randomUUID().toString(); } while (ids.contains(id));

        return id;
    }
}
