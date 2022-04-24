package fr.sweetiez.sweets.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetJpaRepository extends JpaRepository<SweetDatabaseModel, Long> {}
