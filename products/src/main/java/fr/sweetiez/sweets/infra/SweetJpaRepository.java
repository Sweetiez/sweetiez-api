package fr.sweetiez.sweets.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SweetJpaRepository extends JpaRepository<SweetDatabaseModel, Long> {

    @Modifying
    @Query(value = "UPDATE sweet SET state = :state, update_author = :author, " +
            "updated = now(), " +
            "priority = :priority " +
            "WHERE id = :id",
            nativeQuery = true)
    void publish(@Param("id") UUID id, @Param("state") Integer state,
                 @Param("priority") Integer priority, @Param("author") UUID author);
//    void publish(@Param("id") UUID id, @Param("state") Integer state, UUID author);
}
