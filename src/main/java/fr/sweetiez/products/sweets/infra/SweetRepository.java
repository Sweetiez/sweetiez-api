package fr.sweetiez.products.sweets.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SweetRepository extends JpaRepository<SweetEntity, Long> {
    Optional<SweetEntity> findById(String id);
    boolean existsByName(String name);
}
