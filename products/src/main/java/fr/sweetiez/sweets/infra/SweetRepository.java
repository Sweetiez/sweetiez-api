package fr.sweetiez.sweets.infra;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;
import fr.sweetiez.sweets.use_cases.CreateSweetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class SweetRepository implements Sweets {

    private final JpaRepository<SweetDatabaseModel, Long> jpaRepository;

    public SweetRepository(JpaRepository<SweetDatabaseModel, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Optional<UUID> save(Sweet sweet, UUID creator) {
        try {
            SweetDatabaseModel.SweetBuilder builder = new SweetDatabaseModel.SweetBuilder();
            builder.id(sweet.getId().getId());
            builder.name(sweet.getName());
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

    public Set<Sweet> all() {
        /*var sweets = new HashSet<Sweet>();
        var dto = new CreateSweetRequest(
                "So good",
                new HashSet<>(List.of("a", "b", "c")),
                BigDecimal.valueOf(0.95));
        var sweet = new Sweet(dto, sweets);
        sweets.add(sweet);

        return sweets;*/
        return null;
    }

    public Optional<Sweet> findByID(String id) {
        return Optional.empty();
    }
}
