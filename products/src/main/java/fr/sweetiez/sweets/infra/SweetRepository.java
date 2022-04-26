package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SweetRepository implements Sweets {

    private final SweetJpaRepository jpaRepository;

    @Autowired
    public SweetRepository(SweetJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Optional<UUID> save(Sweet sweet, UUID creator) {
        try {
            SweetDatabaseModel.SweetBuilder builder = new SweetDatabaseModel.SweetBuilder();
            builder.id(sweet.getId().getId());
            builder.name(sweet.getName());
            builder.price(sweet.getPrice());
            builder.description(sweet.getDescription());
            builder.type(sweet.getType());
            builder.priority(sweet.getPriority());
            builder.creator(creator);
            builder.state(sweet.getStatus());

            SweetDatabaseModel model = jpaRepository.save(builder.build());
            return Optional.of(model.getId());
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }

    @Transactional
    public void update(Sweet publishedSweet, UUID author) {
        jpaRepository.publish(
                publishedSweet.getId().getId(),
                publishedSweet.getStatus().ordinal(),
                publishedSweet.getPriority().ordinal(),
                author
        );
    }

    public Set<Sweet> all() {
        return jpaRepository.findAll().stream()
            .map(SweetDatabaseModel::toSweet)
            .collect(Collectors.toSet());
    }

    public Optional<Sweet> findByID(String id) {
        return all().stream()
                .filter(sweet -> sweet.getId().toString().equals(id))
                .findFirst();
    }
}
